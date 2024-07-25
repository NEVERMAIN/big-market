package com.openicu.infrastructure.persistent.repository;

import com.alibaba.fastjson.JSON;
import com.myapp.middleware.db.router.strategy.IDBRouterStrategy;
import com.openicu.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.domain.award.model.entity.TaskEntity;
import com.openicu.infrastructure.event.EventPublisher;
import com.openicu.infrastructure.persistent.dao.ITaskDao;
import com.openicu.infrastructure.persistent.dao.IUserAwardRecordDao;
import com.openicu.infrastructure.persistent.dao.IUserRaffleOrderDao;
import com.openicu.infrastructure.persistent.po.Task;
import com.openicu.infrastructure.persistent.po.UserAwardRecord;
import com.openicu.infrastructure.persistent.po.UserRaffleOrder;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 奖品仓储服务
 * @author: 云奇迹
 * @date: 2024/7/19
 */
@Slf4j
@Repository
public class AwardRepository implements IAwardRepository {

    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private IUserAwardRecordDao userAwardRecordDao;

    @Resource
    private IUserRaffleOrderDao userRaffleOrderDao;

    @Resource
    private ITaskDao taskDao;

    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;


    @Override
    public void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate) {

        UserAwardRecordEntity userAwardRecordEntity = userAwardRecordAggregate.getUserAwardRecordEntity();
        TaskEntity taskEntity = userAwardRecordAggregate.getTaskEntity();
        String userId = userAwardRecordEntity.getUserId();
        Long activityId = userAwardRecordEntity.getActivityId();
        Integer awardId = userAwardRecordEntity.getAwardId();

        // 1.构建 用户奖品记录持久化对象
        UserAwardRecord userAwardRecord = UserAwardRecord.builder()
                .userId(userAwardRecordEntity.getUserId())
                .strategyId(userAwardRecordEntity.getStrategyId())
                .activityId(userAwardRecordEntity.getActivityId())
                .orderId(userAwardRecordEntity.getOrderId())
                .awardId(userAwardRecordEntity.getAwardId())
                .awardTitle(userAwardRecordEntity.getAwardTitle())
                .awardTime(userAwardRecordEntity.getAwardTime())
                .awardState(userAwardRecordEntity.getAwardState().getCode())
                .build();

        // 2.构建 任务持久化对象
        Task task = Task.builder()
                .userId(userId)
                .topic(taskEntity.getTopic())
                .messageId(taskEntity.getMessageId())
                .message(JSON.toJSONString(taskEntity.getMessage()))
                .state(taskEntity.getState().getCode())
                .build();

        // 3.用户抽奖订单
        UserRaffleOrder userRaffleOrderReq = new UserRaffleOrder();
        userRaffleOrderReq.setUserId(userAwardRecordEntity.getUserId());
        userRaffleOrderReq.setOrderId(userAwardRecordEntity.getOrderId());

        try{
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
               try{

                   // 1.写入记录
                   userAwardRecordDao.insert(userAwardRecord);
                   // 2.写入任务
                   taskDao.insert(task);
                   // 3. 更新数据库记录
                   Integer count = userRaffleOrderDao.updateUserRaffleOrderStateUsed(userRaffleOrderReq);
                   if(1 != count){
                       status.setRollbackOnly();
                       log.error("写入中奖记录，用户抽奖单已使用过，不可重复抽奖 userId: {} activityId: {} awardId: {}", userId, activityId, awardId);
                       throw new AppException(ResponseCode.ACTIVITY_ORDER_ERROR.getCode(), ResponseCode.ACTIVITY_ORDER_ERROR.getInfo());
                   }

                   return 1;
               }catch (DuplicateKeyException e){
                   status.setRollbackOnly();
                   log.error("写入中奖记录，唯一索引冲突 userId: {} activityId: {} awardId: {}", userId, activityId, awardId, e);
                   throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
               }
            });

        }finally {
            dbRouter.clear();
        }

        // 异步执行发送 MQ
        threadPoolExecutor.execute(()->{
            // 发送 MQ 消息,通知发货。
            try{
                // 1.发送消息【在事务外执行,如果失败还有任务补偿】
                eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
                // 2. 更新数据库记录, task 任务表
                taskDao.updateTaskMessageCompleted(task);
            }catch (Exception e){
                log.error("写入中奖记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
                taskDao.updateTaskSendMessageFail(task);
            }
        });


    }
}

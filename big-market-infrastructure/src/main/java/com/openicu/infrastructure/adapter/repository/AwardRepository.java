package com.openicu.infrastructure.adapter.repository;


import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson2.JSON;
import com.openicu.domain.award.model.aggregate.GiveOutPrizesAggregate;
import com.openicu.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.openicu.domain.award.model.entity.TaskEntity;
import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import com.openicu.domain.award.model.entity.UserCreditAwardEntity;
import com.openicu.domain.award.model.valobj.AccountStatusVO;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.infrastructure.dao.*;
import com.openicu.infrastructure.dao.po.Task;
import com.openicu.infrastructure.dao.po.UserAwardRecord;
import com.openicu.infrastructure.dao.po.UserCreditAccount;
import com.openicu.infrastructure.dao.po.UserRaffleOrder;
import com.openicu.infrastructure.event.EventPublisher;
import com.openicu.infrastructure.redis.IRedisService;
import com.openicu.types.common.Constants;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private IUserCreditAccountDao userCreditAccountDao;

    @Resource
    private IAwardDao awardDao;

    @Resource
    private ITaskDao taskDao;

    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private IRedisService redisService;


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

        try {

            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {

                    // 1.写入记录
                    userAwardRecordDao.insert(userAwardRecord);
                    // 2.写入任务
                    taskDao.insert(task);
                    // 3. 更新数据库记录
                    Integer count = userRaffleOrderDao.updateUserRaffleOrderStateUsed(userRaffleOrderReq);

                    if (1 != count) {
                        status.setRollbackOnly();
                        log.error("写入中奖记录，用户抽奖单已使用过，不可重复抽奖 userId: {} activityId: {} awardId: {}", userId, activityId, awardId);
                        throw new AppException(ResponseCode.ACTIVITY_ORDER_ERROR.getCode(), ResponseCode.ACTIVITY_ORDER_ERROR.getInfo());
                    }

                    return 1;

                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入中奖记录，唯一索引冲突 userId: {} activityId: {} awardId: {}", userId, activityId, awardId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });

        } finally {
            dbRouter.clear();
        }

        // 异步执行发送 MQ
        threadPoolExecutor.execute(() -> {
            // 发送 MQ 消息,通知发货
            try {
                // 1.发送消息【在事务外执行,如果失败还有任务补偿】
                eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
                // 2. 更新数据库记录, task 任务表
                taskDao.updateTaskMessageCompleted(task);
                log.info("写入中奖记录，发送MQ消息完成 userId: {} orderId:{} topic: {}", userId, userAwardRecordEntity.getOrderId(), task.getTopic());
            } catch (Exception e) {
                log.error("写入中奖记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
                taskDao.updateTaskSendMessageFail(task);
            }
        });

    }

    @Override
    public String queryAwardConfig(Integer awardId) {
        return awardDao.queryAwardConfig(awardId);
    }

    @Override
    public String queryAwardKey(Integer awardId) {
        return awardDao.queryAwardKey(awardId);
    }

    @Override
    public void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate) {

        String userId = giveOutPrizesAggregate.getUserId();
        UserCreditAwardEntity userCreditAwardEntity = giveOutPrizesAggregate.getUserCreditAwardEntity();
        UserAwardRecordEntity userAwardRecordEntity = giveOutPrizesAggregate.getUserAwardRecordEntity();

        // 更新发奖记录
        UserAwardRecord userAwardRecordReq = new UserAwardRecord();
        userAwardRecordReq.setUserId(userId);
        userAwardRecordReq.setOrderId(userAwardRecordEntity.getOrderId());
        userAwardRecordReq.setAwardState(userAwardRecordEntity.getAwardState().getCode());

        // 更新用户积分,【首次则插入数据】
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        userCreditAccountReq.setTotalAmount(userCreditAwardEntity.getCreditAmount());
        userCreditAccountReq.setAvailableAmount(userCreditAwardEntity.getCreditAmount());
        userCreditAccountReq.setAccountStatus(AccountStatusVO.open.getCode());

        RLock Lock = redisService.getLock(Constants.RedisKey.ACTIVITY_ACCOUNT_LOCK + userId);
        try {
            Lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(giveOutPrizesAggregate.getUserId());
            transactionTemplate.execute(status -> {
                try {

                    // 1. 更新积分 || 创建积分账户
                    UserCreditAccount userCreditAccountRes = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (null == userCreditAccountRes) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    }


                    // 更新奖品记录
                    int updateAwardCount = userAwardRecordDao.updateAwardRecordCompletedState(userAwardRecordReq);
                    if (0 == updateAwardCount) {
                        log.warn("更新中奖记录，重复更新拦截 userId:{} giveOutPrizesAggregate:{}", userId, JSON.toJSONString(giveOutPrizesAggregate));
                        status.setRollbackOnly();
                    }

                    return 1;

                } catch (DuplicateKeyException e) {

                    status.setRollbackOnly();
                    log.error("更新中奖记录,唯一索引冲突 userId:{}", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);

                }
            });


        } finally {
            dbRouter.clear();
            Lock.unlock();
        }


    }


}

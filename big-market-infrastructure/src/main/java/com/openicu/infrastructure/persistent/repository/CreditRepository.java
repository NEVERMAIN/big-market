package com.openicu.infrastructure.persistent.repository;

import com.alibaba.fastjson.JSON;
import com.myapp.middleware.db.router.strategy.IDBRouterStrategy;
import com.openicu.domain.award.model.valobj.AccountStatusVO;
import com.openicu.domain.credit.model.aggregate.TradeAggregate;
import com.openicu.domain.credit.model.entity.CreditAccountEntity;
import com.openicu.domain.credit.model.entity.CreditOrderEntity;
import com.openicu.domain.credit.model.entity.TaskEntity;
import com.openicu.domain.credit.repository.ICreditRepository;
import com.openicu.infrastructure.event.EventPublisher;
import com.openicu.infrastructure.persistent.dao.ITaskDao;
import com.openicu.infrastructure.persistent.dao.IUserCreditAccountDao;
import com.openicu.infrastructure.persistent.dao.IUserCreditOrderDao;
import com.openicu.infrastructure.persistent.po.Task;
import com.openicu.infrastructure.persistent.po.UserCreditAccount;
import com.openicu.infrastructure.persistent.po.UserCreditOrder;
import com.openicu.infrastructure.persistent.redis.IRedisService;
import com.openicu.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Lock;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Slf4j
@Repository
public class  CreditRepository implements ICreditRepository {

    @Resource
    private IUserCreditOrderDao userCreditOrderDao;

    @Resource
    private IUserCreditAccountDao userCreditAccountDao;

    @Resource
    private ITaskDao taskDao;

    @Resource
    private IRedisService redisService;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private EventPublisher eventPublisher;


    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {

        String userId = tradeAggregate.getUserId();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        TaskEntity taskEntity = tradeAggregate.getTaskEntity();

        // 积分账户
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        userCreditAccountReq.setTotalAmount(creditAccountEntity.getAdjustAmount());
        // 知识；仓储往上有业务语义，仓储往下到 dao 操作是没有业务语义的。所以不用在乎这块使用的字段名称，直接用持久化对象即可。
        userCreditAccountReq.setAvailableAmount(creditAccountEntity.getAdjustAmount());
        userCreditAccountReq.setAccountStatus(AccountStatusVO.open.getCode());

        // 创建积分订单流水
        UserCreditOrder userCreditOrderReq = new UserCreditOrder();
        userCreditOrderReq.setUserId(creditOrderEntity.getUserId());
        userCreditOrderReq.setOrderId(creditOrderEntity.getOrderId());
        userCreditOrderReq.setTradeName(creditOrderEntity.getTradeName().getName());
        userCreditOrderReq.setTradeType(creditOrderEntity.getTradeType().getCode());
        userCreditOrderReq.setTradeAmount(creditOrderEntity.getTradeAmount());
        userCreditOrderReq.setOutBusinessNo(creditOrderEntity.getOutBusinessNo());

        // 构建消息
        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());


        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());
        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            // 编程式事务
            transactionTemplate.execute(status -> {

                try {

                    // 1.保存账户积分
                    UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (null == userCreditAccount) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    }

                    // 2.保存账户订单
                    userCreditOrderDao.insert(userCreditOrderReq);

                    // 3.写入任务
                    taskDao.insert(task);

                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度异常，唯一索引冲突 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度失败 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                }
                return 1;
            });

        } finally {
            dbRouter.clear();
            lock.unlock();
        }

        try{
            // 发送消息【在事务外执行,如果失败还有任务补偿】
            // credit_adjust_success
            eventPublisher.publish(task.getTopic(),task.getMessage());
            // 更新数据库记录, task 任务表
            taskDao.updateTaskMessageCompleted(task);
            log.info("调整账户积分记录,发送MQ消息完成 userId:{} orderId:{} topic:{}",userId,creditOrderEntity.getOrderId(),task.getTopic());
        }catch (Exception e){
            log.error("调整账户积分记录,发送MQ消息失败 userId:{} topic:{}",userId,task.getTopic(),e);
            taskDao.updateTaskSendMessageFail(task);
        }

    }

}

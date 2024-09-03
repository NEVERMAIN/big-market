package com.openicu.trigger.job;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.openicu.domain.task.model.entity.TaskEntity;
import com.openicu.domain.task.service.ITaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Slf4j
@Component
public class SendMessageTaskJob {

    @Resource
    private ITaskService taskService;

    @Resource
    private ThreadPoolExecutor executor;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private RedissonClient redissonClient;


    /**
     * 本地化任务注解: @Scheduled(cron = "0/5 * * * * ?")
     * 分布式任务注解: @XxlJob("SendMessageTaskJob_DB1")
     */
    @XxlJob("SendMessageTaskJob_DB1")
    public void exec_01() {

        RLock lock = redissonClient.getLock("big-market-SendMessageTaskJob_DB1");
        boolean isLock = false;

        try {
            // 1. 抢占式执行定时任务,抢占分布式
            isLock = lock.tryLock(3, 0, TimeUnit.SECONDS);
            if (!isLock) return;
            // 2.设置分库分表路由
            dbRouter.setDBKey(1);
            dbRouter.setTBKey(0);
            List<TaskEntity> taskEntityList = taskService.queryNoSendMessageTaskList();
            if (taskEntityList.isEmpty()) return;
            // 3.发送 MQ 消息
            for (TaskEntity taskEntity : taskEntityList) {
                // 开启线程发送，提高发送效率。配置的线程池策略为 CallerRunsPolicy，在 ThreadPoolConfig 配置中有4个策略，面试中容易对比提问。可以检索下相关资料。
                executor.execute(() -> {
                    try {
                        // 1.发送 MQ 消息
                        taskService.sendMessage(taskEntity);
                        // 2.修改数据库任务表总状态
                        taskService.updateTaskSendMessageCompleted(taskEntity.getUserId(), taskEntity.getMessageId());
                    } catch (Exception e) {
                        log.error("定时任务，发送MQ消息失败 userId: {} topic: {}", taskEntity.getUserId(), taskEntity.getTopic());
                        taskService.updateTaskSendMessageFail(taskEntity.getUserId(), taskEntity.getMessageId());
                    }
                });
            }

        } catch (Exception e) {
            log.error("定时任务,扫描MQ任务发送消息失败。", e);
        } finally {
            dbRouter.clear();
            if (isLock) {
                lock.unlock();
            }
        }

    }

    /**
     * 本地化任务注解: @Scheduled(cron = "0/5 * * * * ?")
     * 分布式任务注解: @XxlJob("SendMessageTaskJob_DB2")
     */
    @XxlJob("SendMessageTaskJob_DB2")
    public void exec_02() {
        RLock lock = redissonClient.getLock("big-market-SendMessageTaskJob_DB2");
        boolean isLock = false;
        try {
            // 1.抢占式执行定时任务,抢占分布式
            isLock = lock.tryLock(3, 0, TimeUnit.SECONDS);
            if(!isLock) return;
            // 2.设置路由
            dbRouter.setDBKey(2);
            dbRouter.setTBKey(0);
            List<TaskEntity> taskEntityList = taskService.queryNoSendMessageTaskList();
            if (taskEntityList.isEmpty()) return;
            // 3.发送 MQ 消息
            for (TaskEntity taskEntity : taskEntityList) {
                // 开启线程发送，提高发送效率。配置的线程池策略为 CallerRunsPolicy，在 ThreadPoolConfig 配置中有4个策略，面试中容易对比提问。可以检索下相关资料。
                executor.execute(() -> {
                    try {
                        // 1.发送 MQ 消息
                        taskService.sendMessage(taskEntity);
                        // 2.修改数据库任务表总状态
                        taskService.updateTaskSendMessageCompleted(taskEntity.getUserId(), taskEntity.getMessageId());
                    } catch (Exception e) {
                        log.error("定时任务，发送MQ消息失败 userId: {} topic: {}", taskEntity.getUserId(), taskEntity.getTopic());
                        taskService.updateTaskSendMessageFail(taskEntity.getUserId(), taskEntity.getMessageId());
                    }
                });
            }

        } catch (Exception e) {
            log.error("定时任务,扫描MQ任务发送消息失败。", e);
        } finally {
            dbRouter.clear();
            if(isLock){
                lock.unlock();
            }
        }

    }


}

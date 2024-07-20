package com.openicu.trigger.job;

import com.myapp.middleware.db.router.annotation.DBRouterStrategy;
import com.myapp.middleware.db.router.strategy.IDBRouterStrategy;
import com.openicu.domain.task.model.entity.TaskEntity;
import com.openicu.domain.task.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

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

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec(){
        try{

            // 1.获取分库数量
            int dbCount = dbRouter.dbCount();

            // 逐个库扫描【每个库一个任务表】
            for(int dbIdx = 1 ; dbIdx <= dbCount ; dbIdx++){
                int finalDbIdx = dbIdx;
                executor.execute(()->{
                    try{
                        dbRouter.setDbKey(finalDbIdx);
                        dbRouter.setTbKey(0);
                        List<TaskEntity> taskEntityList = taskService.queryNoSendMessageTaskList();
                        if(taskEntityList.isEmpty()) return;
                        // 发送 MQ 消息
                        for (TaskEntity taskEntity : taskEntityList) {
                            // 开启线程发送，提高发送效率。配置的线程池策略为 CallerRunsPolicy，在 ThreadPoolConfig 配置中有4个策略，面试中容易对比提问。可以检索下相关资料。
                            executor.execute(()->{
                                try{
                                    // 1.发送 MQ 消息
                                    taskService.sendMessage(taskEntity);
                                    // 2.修改数据库任务表总状态
                                    taskService.updateTaskSendMessageCompleted(taskEntity.getUserId(),taskEntity.getMessageId());
                                }catch (Exception e){
                                    log.error("定时任务，发送MQ消息失败 userId: {} topic: {}", taskEntity.getUserId(), taskEntity.getTopic());
                                    taskService.updateTaskSendMessageFail(taskEntity.getUserId(),taskEntity.getMessageId());
                                }
                            });
                        }

                    }finally {
                        dbRouter.clear();
                    }
                });
            }

        }catch (Exception e){
            log.error("定时任务,扫描MQ任务发送消息失败。", e);
        }finally {
            dbRouter.clear();
        }

    }


}

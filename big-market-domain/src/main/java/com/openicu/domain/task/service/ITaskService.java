package com.openicu.domain.task.service;

import com.openicu.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/19
 */
public interface ITaskService {

    /**
     * 查询发送MQ失败和超时1分钟未发送的MQ消息
     * @return
     */
    List<TaskEntity> queryNoSendMessageTaskList();

    /**
     * 发送消息给 MQ
     * @param taskEntity
     */
    void sendMessage(TaskEntity taskEntity);

    /**
     * 更新任务消息状态为发送成功
     * @param userId
     * @param messageId
     */
    void updateTaskSendMessageCompleted(String userId,String messageId);

    /**
     * 更新任务消息状态为发送失败
     * @param userId
     * @param messageId
     */
    void updateTaskSendMessageFail(String userId,String messageId);

}

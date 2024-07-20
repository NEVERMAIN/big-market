package com.openicu.domain.task.repository;

import com.openicu.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/19
 */
public interface ITaskRepository {

    /**
     * 查询未发送消息的任务列表。
     *
     * @return 未发送消息的任务列表，列表中包含TaskEntity对象。
     */
    List<TaskEntity> queryNoSendMessageTaskList();

    /**
     * 发送消息。
     *
     * @param taskEntity 要发送的消息任务实体。
     */
    void sendMessage(TaskEntity taskEntity);

    /**
     * 更新任务为消息发送成功状态。
     *
     * @param userId 用户ID，标识接收消息的用户。
     * @param messageId 消息ID，用于唯一标识发送的消息。
     */
    void updateTaskSendMessageCompleted(String userId, String messageId);

    /**
     * 更新任务为消息发送失败状态。
     *
     * @param userId 用户ID，标识接收消息的用户。
     * @param messageId 消息ID，用于唯一标识发送的消息。
     */
    void updateTaskSendMessageFail(String userId, String messageId);

}

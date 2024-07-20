package com.openicu.domain.task.repository;

import com.openicu.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/19
 */
public interface ITaskRepository {

    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);

}

package com.openicu.infrastructure.adapter.repository;

import com.openicu.domain.task.model.entity.TaskEntity;
import com.openicu.domain.task.repository.ITaskRepository;
import com.openicu.infrastructure.event.EventPublisher;
import com.openicu.infrastructure.dao.ITaskDao;
import com.openicu.infrastructure.dao.po.Task;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 任务仓储服务
 * @author: 云奇迹
 * @date: 2024/7/19
 */
@Repository
public class TaskRepository implements ITaskRepository {

    @Resource
    private ITaskDao taskDao;

    @Resource
    private EventPublisher eventPublisher;

    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {

        // 1. 查询发送MQ失败和超时1分钟未发送的MQ
        List<Task> taskList = taskDao.queryNoSendMessageTaskList();
        // 2.转成 TaskEntity 列表为 Task 列表
        return taskList.stream().map(task -> TaskEntity.builder()
                .userId(task.getUserId())
                .topic(task.getTopic())
                .messageId(task.getMessageId())
                .message(task.getMessage())
                .build()).collect(Collectors.toList());

    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        // 1.向 MQ 发送消息
        eventPublisher.publish(taskEntity.getTopic(),taskEntity.getMessage());
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        Task req = new Task();
        req.setUserId(userId);
        req.setMessageId(messageId);
        taskDao.updateTaskMessageCompleted(req);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        Task req = new Task();
        req.setUserId(userId);
        req.setMessageId(messageId);
        taskDao.updateTaskSendMessageFail(req);
    }
}

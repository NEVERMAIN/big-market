package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.openicu.infrastructure.persistent.po.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 任务表DAO
 * @author: 云奇迹
 * @date: 2024/7/16
 */
@Mapper
public interface ITaskDao {

    /**
     * 创建 任务消息记录
     * @param task
     */
    void insert(Task task);

    /**
     * 更新任务消息为完成状态
     * @param task
     */
    @DBRouter
    void updateTaskMessageCompleted(Task task);

    /**
     * 更新任务状态为失败总状态
     * @param task
     */
    @DBRouter
    void updateTaskSendMessageFail(Task task);

    /**
     * 查询发送状态为失败的任务任务列表
     * @return 任务列表
     */
    List<Task> queryNoSendMessageTaskList();


}

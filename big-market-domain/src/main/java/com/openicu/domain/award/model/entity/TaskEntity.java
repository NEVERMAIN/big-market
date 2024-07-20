package com.openicu.domain.award.model.entity;

import com.openicu.domain.award.model.valobj.TaskStateVO;
import com.openicu.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    /** 活动ID */
    private String userId;
    /** 消息主题 */
    private String topic;
    /** 消息编号 */
    private String messageId;
    /** 消息主体 */
    private BaseEvent.EventMessage<?> message;
    /** 任务状态: create-创建、completed-完成、fail-失败 */
    private TaskStateVO state;

}

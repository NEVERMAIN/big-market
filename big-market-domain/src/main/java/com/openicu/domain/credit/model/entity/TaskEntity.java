package com.openicu.domain.credit.model.entity;

import com.openicu.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.openicu.domain.rebate.event.SendRebateMessageEvent;
import com.openicu.domain.rebate.model.valobj.TaskStateVO;
import com.openicu.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    private String userId;

    private String topic;

    private String messageId;
    /** 消息主体 */
    private BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message;
    /** 任务状态；create-创建、completed-完成、fail-失败 */
    private TaskStateVO state;
}

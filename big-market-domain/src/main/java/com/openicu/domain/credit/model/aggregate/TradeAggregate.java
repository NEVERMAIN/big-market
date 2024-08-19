package com.openicu.domain.credit.model.aggregate;

import com.openicu.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.openicu.domain.credit.model.entity.CreditAccountEntity;
import com.openicu.domain.credit.model.entity.CreditOrderEntity;
import com.openicu.domain.credit.model.entity.TaskEntity;
import com.openicu.domain.credit.model.valobj.TradeNameVO;
import com.openicu.domain.credit.model.valobj.TradeTypeVO;
import com.openicu.domain.rebate.model.valobj.TaskStateVO;
import com.openicu.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeAggregate {

    /** 用户ID */
    private String userId;
    /** 积分账户实体 */
    private CreditAccountEntity creditAccountEntity;
    /** 积分订单实体 */
    private CreditOrderEntity creditOrderEntity;
    /** 任务实体 - 补偿 MQ 消息 */
    private TaskEntity taskEntity;


    public static CreditAccountEntity buildCreditAccountEntity(String userId, BigDecimal amount) {
        return CreditAccountEntity.builder()
                .userId(userId)
                .adjustAmount(amount)
                .build();
    }

    public static CreditOrderEntity buildCreditOrderEntity(String userId, TradeNameVO tradeName, TradeTypeVO tradeType, BigDecimal amount, String outBusinessNo) {
        return CreditOrderEntity.builder()
                .userId(userId)
                .orderId(RandomStringUtils.randomNumeric(12))
                .tradeName(tradeName)
                .tradeType(tradeType)
                .tradeAmount(amount)
                .outBusinessNo(outBusinessNo)
                .build();
    }

    public static TaskEntity createTaskEntity(String userId, String topic, String messageId, BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message){

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userId);
        taskEntity.setTopic(topic);
        taskEntity.setMessageId(messageId);
        taskEntity.setMessage(message);
        taskEntity.setState(TaskStateVO.create);
        return taskEntity;

    }

}

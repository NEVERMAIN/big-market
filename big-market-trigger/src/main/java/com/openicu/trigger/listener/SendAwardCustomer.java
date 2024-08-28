package com.openicu.trigger.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.openicu.domain.award.event.SendAwardMessageEvent;
import com.openicu.domain.award.model.entity.DistributeAwardEntity;
import com.openicu.domain.award.service.IAwardService;
import com.openicu.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Slf4j
@Component
public class SendAwardCustomer {

    @Value("${spring.rabbitmq.topic.send_award}")
    private String topic;

    @Resource
    private IAwardService awardService;

    @RabbitListener(queuesToDeclare = @Queue(value = "send_award"))
    public void listener(String message) {
        try {
            log.info("监听用户奖品发送消息，发奖开始 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage>>() {
            }.getType());
            SendAwardMessageEvent.SendAwardMessage sendAwardMessage = eventMessage.getData();

            // 发放奖品
            DistributeAwardEntity distributeAwardEntity = new DistributeAwardEntity();
            distributeAwardEntity.setUserId(sendAwardMessage.getUserId());
            distributeAwardEntity.setOrderId(sendAwardMessage.getOrderId());
            distributeAwardEntity.setAwardId(sendAwardMessage.getAwardId());
            distributeAwardEntity.setAwardConfig(sendAwardMessage.getAwardConfig());
            awardService.distributeAward(distributeAwardEntity);

            log.info("监听用户奖品发送消息，发奖完成 topic: {} message: {}", topic, message);
        } catch (Exception e) {

            log.error("监听用户奖品发送消息，消费失败 topic: {} message: {}", topic, message);
        }

    }

}

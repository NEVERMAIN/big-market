package com.openicu.trigger.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
@Component
@Slf4j
public class SendRebateCustomer {

    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message) {

        try {
            log.info("监听用户返利行为 topic:{} message:{}", topic, message);
        } catch (Exception e) {
            log.error("监听用户返利行为,消费失败 topic:{} message:{}", topic, message, e);
            throw e;
        }


    }

}

package com.openicu.infrastructure.event;

import com.alibaba.fastjson.JSON;
import com.openicu.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 消息发送
 * @author: 云奇迹
 * @date: 2024/7/14
 */
@Slf4j
@Component
public class EventPublisher {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage){
        try{

            String messageJson = JSON.toJSONString(eventMessage);
            rabbitTemplate.convertAndSend(topic,messageJson);
            log.info("发送MQ消息 topic:{} message:{}", topic, messageJson);

        }catch (Exception e){
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;

        }
    }



}

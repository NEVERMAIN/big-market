package com.openicu.trigger.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public void listener(String message){
        try{
            log.info("监听用户奖品发送消息 topic: {} message:{}",topic,message);
        }catch (Exception e){
            log.error("监听用户奖品发送消息,消费失败 topic:{} message:{} ", topic,message);
            throw e;
        }
    }

}

package com.openicu.trigger.listener;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.award.event.SendAwardMessageEvent;
import com.openicu.domain.award.model.aggregate.UserAwardDistributionAggregate;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.domain.award.service.distribute.IAwardDistribution;
import com.openicu.domain.award.service.distribute.factory.AwardDistributionFactory;
import com.openicu.domain.award.model.entity.AwardEntity;
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
    private AwardDistributionFactory awardDistributionFactory;

    @Resource
    private IAwardRepository awardRepository;


    @RabbitListener(queuesToDeclare = @Queue(value = "send_award"))
    public void listener(String message) {
        try {

            log.info("监听用户奖品发送消息 topic: {} message:{}", topic, message);
            Object data = JSON.parseObject(message, BaseEvent.EventMessage.class).getData();
            SendAwardMessageEvent.SendAwardMessage sendAwardMessage = JSON.parseObject(JSON.toJSONString(data), SendAwardMessageEvent.SendAwardMessage.class);

            // 1.查询奖品的类型
            Integer awardId = sendAwardMessage.getAwardId();
            String userId = sendAwardMessage.getUserId();
            String orderId = sendAwardMessage.getOrderId();

            AwardEntity awardEntity = awardRepository.queryAwardByAwardId(awardId);
            IAwardDistribution awardDistribution = awardDistributionFactory.getAwardDistribution(awardEntity.getAwardKey());

            UserAwardDistributionAggregate userAwardDistributionAggregate = new UserAwardDistributionAggregate();
            userAwardDistributionAggregate.setUserId(userId);
            userAwardDistributionAggregate.setAwardEntity(awardEntity);
            userAwardDistributionAggregate.setOrderId(orderId);

            awardDistribution.doDistribution(userAwardDistributionAggregate);


        } catch (Exception e) {
            log.error("监听用户奖品发送消息,消费失败 topic:{} message:{} ", topic, message);
            throw e;
        }
    }

}

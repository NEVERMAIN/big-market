package com.openicu.trigger.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.openicu.domain.activity.model.entity.SkuRechargeEntity;
import com.openicu.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.openicu.domain.rebate.event.SendRebateMessageEvent;
import com.openicu.domain.rebate.model.valobj.RebateTypeVO;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.event.BaseEvent;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
@Component
@Slf4j
public class RebateMessageCustomer {

    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;

    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;


    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message) {

        try {

            log.info("监听用户返利信息 topic: {} message: {}", topic, message);
            // 1.转换消息
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {
            }.getType());
            SendRebateMessageEvent.RebateMessage rebateMessage = eventMessage.getData();
            if (!RebateTypeVO.SKU.getCode().equals(rebateMessage.getRebateType())) {
                log.info("监听用户行为返利信息 - 非sku奖励暂时不处理 topic: {} message: {}", topic, message);
                return;
            }

            // 2.入账消息
            SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
            skuRechargeEntity.setUserId(rebateMessage.getUserId());
            skuRechargeEntity.setSku(Long.valueOf(rebateMessage.getRebateConfig()));
            skuRechargeEntity.setOutBusinessNo(rebateMessage.getBizId());
            raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);

        } catch (AppException e) {

            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.warn("监听用户返利行为信息,消费失败 topic: {} message: {}", topic, message);
                return;
            }
            throw e;

        } catch (Exception e) {

            log.error("监听用户返利信息,消费失败 topic: {} message: {}", topic, message, e);
            throw e;

        }


    }

}

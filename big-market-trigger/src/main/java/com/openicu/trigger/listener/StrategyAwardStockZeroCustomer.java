package com.openicu.trigger.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.openicu.domain.activity.service.ISkuStock;
import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.openicu.domain.strategy.service.IRaffleStock;
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
 * @date: 2024/7/15
 */
@Slf4j
@Component
public class StrategyAwardStockZeroCustomer {

    @Value("${spring.rabbitmq.topic.strategy_award_stock_zero}")
    private String topic;

    @Resource
    private IRaffleStock raffleStock;

    @RabbitListener(queuesToDeclare = @Queue(value = "strategy_award_stock_zero"))
    public void listener(String message){

        try{

            log.info("监听策略奖品库存消耗为0消息 topic: {} message: {}", topic, message);
            // 1. 转换对象
            BaseEvent.EventMessage<StrategyAwardStockKeyVO> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<StrategyAwardStockKeyVO>>() {
            }.getType());
            // 2.更新数据库商品库存
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = eventMessage.getData();
            raffleStock.clearStrategyAwardStock(strategyAwardStockKeyVO.getStrategyId(),strategyAwardStockKeyVO.getAwardId());
            // 3.清空延迟队列【此时就不需要延迟更新数据库记录】
            raffleStock.clearQueueValue();

        }catch (Exception e){
            log.error("监听策略奖品库存消耗为0消息，消费失败 topic: {} message: {}", topic, message);
            throw e;

        }

    }

}

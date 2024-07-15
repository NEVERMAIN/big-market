package com.openicu.domain.strategy.event;

import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.openicu.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/15
 */
@Component
public class StrategyAwardStockZeroMessageEvent extends BaseEvent<StrategyAwardStockKeyVO> {

    @Value("${spring.rabbitmq.topic.strategy_award_stock_zero}")
    private String topic;

    @Override
    public EventMessage<StrategyAwardStockKeyVO> buildEventMessage(StrategyAwardStockKeyVO strategyAwardStockKeyVO) {
        return EventMessage.<StrategyAwardStockKeyVO>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(strategyAwardStockKeyVO)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }
}

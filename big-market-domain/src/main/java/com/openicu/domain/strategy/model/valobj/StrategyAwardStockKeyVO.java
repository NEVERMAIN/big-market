package com.openicu.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 策略奖品库存Key标识值对象
 * @author: 云奇迹
 * @date: 2024/6/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyAwardStockKeyVO {

    /** 策略ID */
    private Long strategyId;
    /** 奖品ID */
    private Integer awardId;

}

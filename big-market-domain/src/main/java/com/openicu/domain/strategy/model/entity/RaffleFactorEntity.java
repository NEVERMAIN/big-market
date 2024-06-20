package com.openicu.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 抽奖因子实体
 * @author: 云奇迹
 * @date: 2024/6/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleFactorEntity {

    private String userId;
    private Long strategyId;

}

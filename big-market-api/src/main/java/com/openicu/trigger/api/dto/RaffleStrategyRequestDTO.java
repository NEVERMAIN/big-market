package com.openicu.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 抽奖请求参数
 * @author: 云奇迹
 * @date: 2024/6/26
 */
@Data
public class RaffleStrategyRequestDTO implements Serializable {

    /** 抽奖策略ID */
    private Long strategyId;
}

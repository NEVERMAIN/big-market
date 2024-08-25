package com.openicu.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 抽奖策略规则，权重配置，查询N次抽奖可解锁奖品范围，请求对象
 * @author: 云奇迹
 * @date: 2024/8/7
 */
@Data
public class RaffleStrategyRuleWeightRequestDTO implements Serializable {

    /** 用户ID */
    private String userId;
    /** 抽奖活动ID */
    private Long activityId;

}

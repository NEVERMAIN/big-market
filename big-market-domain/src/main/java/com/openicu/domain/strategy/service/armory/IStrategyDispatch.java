package com.openicu.domain.strategy.service.armory;

/**
 * @description: 调度接口
 * @author: 云奇迹
 * @date: 2024/6/16
 */
public interface IStrategyDispatch {

    /**
     * 根据策略ID获取随机奖励ID。
     *
     * @param strategyId 策略ID，用于确定随机奖励的范围或方式。
     * @return 随机奖励的ID。
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     * 根据策略ID和规则权重值获取随机奖励ID。
     *
     * @param strategyId 策略ID，用于确定随机奖励的范围或方式。
     * @param ruleWeightValue 规则权重值，用于调整随机选择奖励时的权重分布。
     * @return 随机奖励的ID。
     */
    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);

}

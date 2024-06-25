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
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param key = strategyId + _ + ruleWeightValue；
     * @return 抽奖结果
     */
    Integer getRandomAwardId(String key);

    /**
     * 根据策略ID和奖品ID,扣减奖品缓存库存
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId,Integer awardId);

}

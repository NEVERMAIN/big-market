package com.openicu.domain.strategy.service.armory;

/**
 * @description: 调度接口
 * @author: 云奇迹
 * @date: 2024/6/16
 */
public interface IStrategyDispatch {

    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);

}

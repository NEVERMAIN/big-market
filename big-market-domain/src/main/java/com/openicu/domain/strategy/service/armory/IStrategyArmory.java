package com.openicu.domain.strategy.service.armory;

/**
 * @description: 策略装配库(兵工厂),负责初始化策略计算
 * @author: 云奇迹
 * @date: 2024/6/15
 */
public interface IStrategyArmory {


    boolean assembleLotteryStrategy(Long strategyId);

    Integer getRandomAwardId(Long strategyId);



}

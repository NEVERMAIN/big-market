package com.openicu.domain.strategy.service.armory;

/**
 * @description: 策略装配库(兵工厂),负责初始化策略计算
 * @author: 云奇迹
 * @date: 2024/6/15
 */
public interface IStrategyArmory {


    /**
     * 根据策略ID组装彩票策略。
     *
     * @param strategyId 需要组装的彩票策略的唯一标识符。
     * @return 如果策略成功组装，则返回 true；否则返回 false。
     */
    boolean assembleLotteryStrategy(Long strategyId);

    /**
     * 根据活动ID组装彩票策略。
     * @param activityId 活动ID
     * @return 如果策略成功组装，则返回 true；否则返回 false。
     */
    boolean assembleLotteryStrategyByActivityId(Long activityId);
}

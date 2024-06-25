package com.openicu.domain.strategy.service.rule.chain;

import com.openicu.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @description: 抽奖策略规则责任链接口
 * @author: 云奇迹
 * @date: 2024/6/21
 */
public interface ILogicChain extends ILogicChainArmory,Cloneable {

    /**
     * 责任链接口
     *
     * @param userId  用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);

}

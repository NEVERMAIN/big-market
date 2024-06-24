package com.openicu.domain.strategy.service.rule.tree.factory.engine;

import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @description: 规则树组合接口
 * @author: 云奇迹
 * @date: 2024/6/24
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId,Integer awardId);

}

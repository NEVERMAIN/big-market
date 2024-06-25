package com.openicu.domain.strategy.service.rule.tree.factory.engine;

import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @description: 规则树组合接口
 * @author: 云奇迹
 * @date: 2024/6/24
 */
public interface IDecisionTreeEngine {

    /**
     * 处理策略奖励数据。
     *
     * @param userId 用户的唯一标识符。用于确定哪个用户应该获得奖励。
     * @param strategyId 策略的唯一标识符。用于确定是哪个策略触发了奖励。
     * @param awardId 奖励的唯一标识符。用于确定用户将获得哪种奖励。
     * @return 返回一个DefaultTreeFactory.StrategyAwardData对象，其中包含了处理结果。
     */
    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId,Integer awardId);

}

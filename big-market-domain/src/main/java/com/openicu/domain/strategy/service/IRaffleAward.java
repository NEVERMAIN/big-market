package com.openicu.domain.strategy.service;

import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;
import com.openicu.domain.strategy.model.valobj.RuleWeightVO;
import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

import java.util.List;

/**
 * @description: 抽奖奖品相关接口
 * @author: 云奇迹
 * @date: 2024/6/26
 */
public interface IRaffleAward {

    /**
     * 根据策略ID查询奖品列表配置
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

    /**
     * 根据活动ID查询抽奖奖品列表配置
     * @param activityId 活动ID
     * @return
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardListByActivityId(Long activityId);

    /**
     * 查询奖励规则权重
     *
     * @param strategyId 策略ID，用于定位到具体的奖励策略
     * @return 返回一个List，包含该策略下所有奖励规则的权重信息
     */
    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);

    List<StrategyAwardStockKeyVO> queryOpenActivityStrategyAwardList();

}

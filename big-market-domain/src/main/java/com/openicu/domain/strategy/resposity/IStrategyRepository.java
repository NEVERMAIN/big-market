package com.openicu.domain.strategy.resposity;

import com.google.common.annotations.VisibleForTesting;
import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;
import com.openicu.domain.strategy.model.entity.StrategyEntity;
import com.openicu.domain.strategy.model.entity.StrategyRuleEntity;

import java.util.List;
import java.util.Map;

/**
 * @description: 策略仓储接口
 * @author: 云奇迹
 * @date: 2024/6/15
 */
public interface IStrategyRepository {


    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(String key, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTables);

    Integer getStrategyAwardAssemble(String key,Integer rateKey);

    int getRateRange(String key);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

}

package com.openicu.domain.strategy.resposity;

import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;
import java.util.Map;

/**
 * @description: 策略仓储接口
 * @author: 云奇迹
 * @date: 2024/6/15
 */
public interface IStrategyRepository {


    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(Long strategyId, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTables);

    Integer getStrategyAwardAssemble(Long strategyId,Integer rateKey);

    int getRateRange(Long strategyId);




}

package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 策略奖品配置表 Dao
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModels(StrategyAward strategyAward);

    Integer queryStrategyAwardSurplusCount(StrategyAward strategyAward);

    void updateStrategyAwardStock(StrategyAward strategyAward);
}

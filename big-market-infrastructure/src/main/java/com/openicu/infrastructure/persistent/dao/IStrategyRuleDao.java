package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.StrategyAward;
import com.openicu.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 策略规则 Dao
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Mapper
public interface IStrategyRuleDao {

    List<StrategyRule> queryStrategyRuleList();

}

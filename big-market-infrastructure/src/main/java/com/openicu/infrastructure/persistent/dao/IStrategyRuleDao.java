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

    /**
     * 查询策略规则列表。
     *
     * @return 策略规则列表，包含所有可用的策略规则。
     */
    List<StrategyRule> queryStrategyRuleList();

    /**
     * 根据请求条件查询策略规则。
     *
     * @param strategyRuleReq 查询条件对象，包含所需查询的策略规则的条件。
     * @return 符合条件的策略规则。
     */
    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);

    /**
     * 根据策略规则查询其具体值。
     *
     * @param strategyRule 策略规则对象，用于指定要查询的策略规则。
     * @return 策略规则的值，可能是一个字符串表示的规则详情。
     */
    String queryStrategyRuleValue(StrategyRule strategyRule);
}

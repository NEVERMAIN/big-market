package com.openicu.domain.strategy.service;

import com.openicu.domain.strategy.model.valobj.RuleWeightVO;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/25
 */
public interface IRaffleRule {

    /**
     * 根据规则树ID集合查询奖品中加锁数量的配置【部分奖品需要抽奖N次解锁】
     * @param treeIds 规则树ID值
     * @return key 规则树，value rule_lock 加锁值
     */
    Map<String ,Integer> queryAwardRuleLockCount(String[] treeIds);

    /**
     * 根据活动ID查询奖励规则权重
     *
     * @param activityId 活动ID，用于指定要查询的特定活动
     * @return 返回一个List，包含特定活动的奖励规则及其权重
     */
    List<RuleWeightVO> queryAwardRuleWeightByActivityId(Long activityId);

}

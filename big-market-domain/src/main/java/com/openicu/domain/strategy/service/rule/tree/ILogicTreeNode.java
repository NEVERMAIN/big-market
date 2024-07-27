package com.openicu.domain.strategy.service.rule.tree;

import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @description: 规则节点
 * @author: 云奇迹
 * @date: 2024/6/24
 */
public interface ILogicTreeNode {

    /**
     * 执行特定逻辑操作的接口方法。
     *
     * @param userId 用户的唯一标识。用于指定操作与哪个用户相关。
     * @param strategyId 策略的唯一标识。用于指定执行哪种策略。
     * @param awardId 奖品的唯一标识。用于指定分配哪种奖品。
     * @return 返回一个TreeActionEntity对象，该对象包含了操作的结果信息。
     */
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime);

}

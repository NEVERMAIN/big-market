package com.openicu.domain.strategy.service.rule.tree;

import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @description: 规则节点
 * @author: 云奇迹
 * @date: 2024/6/24
 */
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId,Long strategyId,Integer awardId);

}

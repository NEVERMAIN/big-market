package com.openicu.domain.strategy.service.rule.tree.impl;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import org.springframework.stereotype.Component;

/**
 * @description: 次数锁定节点
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }

}

package com.openicu.domain.strategy.service.rule.tree.factory.engine.impl;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.model.valobj.RuleTreeNodeLineVO;
import com.openicu.domain.strategy.model.valobj.RuleTreeNodeVO;
import com.openicu.domain.strategy.model.valobj.RuleTreeVO;
import com.openicu.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.openicu.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @description: 决策树引擎
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Slf4j
public class DecisionTreeEngine implements IDecisionTreeEngine {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    private final RuleTreeVO ruleTreeVO;

    public DecisionTreeEngine(Map<String, ILogicTreeNode> logicTreeNodeGroup, RuleTreeVO ruleTreeVO) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
        this.ruleTreeVO = ruleTreeVO;
    }

    @Override
    public DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId) {

        DefaultTreeFactory.StrategyAwardData strategyAwardData = null;

        // 获取基础信息
        String nextNode = ruleTreeVO.getTreeRootRuleNode();
        Map<String, RuleTreeNodeVO> treeNodeMap = ruleTreeVO.getTreeNodeMap();

        // 获取起始节点【根节点记录了第一个要执行的规则】
        RuleTreeNodeVO ruleTreeNode = treeNodeMap.get(nextNode);
        while (null != ruleTreeNode) {
            // 获取决策节点
            ILogicTreeNode logicTreeNode = logicTreeNodeGroup.get(ruleTreeNode.getRuleKey());
            String ruleValue = ruleTreeNode.getRuleValue();

            // 决策节点计算
            DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId,ruleValue);
            RuleLogicCheckTypeVO ruleLogicCheckTypeVO = logicEntity.getRuleLogicCheckType();
            strategyAwardData = logicEntity.getStrategyAwardData();
            log.info("决策树引擎【{}】 treeId:{} node:{} code:{} info:{}",
                    ruleTreeVO.getTreeName(), ruleTreeVO.getTreeId(), nextNode,
                    ruleLogicCheckTypeVO.getCode(),
                    ruleLogicCheckTypeVO.getInfo()
            );

            // 获取下个节点
            nextNode = nextNode(ruleLogicCheckTypeVO.getCode(), ruleTreeNode.getTreeNodeLineVOList());
            ruleTreeNode = treeNodeMap.get(nextNode);

        }

        return strategyAwardData;
    }

    public String nextNode(String matterValue, List<RuleTreeNodeLineVO> treeNodeLineVOList) {
        // 如果是叶子节点就直接退出
        if (null == treeNodeLineVOList || treeNodeLineVOList.isEmpty()) return null;
        for (RuleTreeNodeLineVO nodeLine : treeNodeLineVOList) {
            if (decisionLogic(matterValue, nodeLine)) {
                return nodeLine.getRuleNodeTo();
            }
        }
        throw new RuntimeException("决策树引擎 nextNode 计算失败,未找到可执行节点");
    }

    public boolean decisionLogic(String matterValue, RuleTreeNodeLineVO nodeLine) {
        switch (nodeLine.getRuleLimitType()) {
            case EQUAL:
                return matterValue.equals(nodeLine.getRuleLimitValue().getCode());
            case GT:
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) > 0;
            case LT:
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) < 0;
            case GE:
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) >= 0;
            case LE:
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) <= 0;
            default:
                return false;
        }
    }
}

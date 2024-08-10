package com.openicu.domain.strategy.service.rule.tree.factory.engine.impl;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.model.valobj.RuleTreeNodeLineVO;
import com.openicu.domain.strategy.model.valobj.RuleTreeNodeVO;
import com.openicu.domain.strategy.model.valobj.RuleTreeVO;
import com.openicu.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.openicu.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
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
    public DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId, Date endDateTime) {

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
            DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId,ruleValue,endDateTime);
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

    /**
     * 根据给定的问题值和规则树节点列表，确定下一个节点
     *
     * @param matterValue 问题值，用于判断下一个节点的依据
     * @param treeNodeLineVOList 规则树节点列表，包含多个规则节点信息
     * @return 返回下一个节点的标识，如果没有找到合适的节点，返回null
     */
    public String nextNode(String matterValue, List<RuleTreeNodeLineVO> treeNodeLineVOList) {
        // 如果是叶子节点就直接退出
        if (null == treeNodeLineVOList || treeNodeLineVOList.isEmpty()) return null;
        // 遍历规则树节点列表，寻找匹配的节点
        for (RuleTreeNodeLineVO nodeLine : treeNodeLineVOList) {
            // 如果问题值符合当前节点的判断逻辑
            if (decisionLogic(matterValue, nodeLine)) {
                // 返回匹配节点的目标节点标识
                return nodeLine.getRuleNodeTo();
            }
        }
       // 如果没有匹配的节点，返回null
       return null;
    }


    /**
     * 根据规则限制类型判断某个值是否满足条件
     *
     * 本函数通过比较传入的值（matterValue）和规则节点线中的限制值（nodeLine.getRuleLimitValue().getCode()），
     * 根据不同的限制类型（nodeLine.getRuleLimitType()）来判断是否满足条件
     *
     * @param matterValue 需要判断的值
     * @param nodeLine 规则节点线对象，包含规则限制类型和限制值
     * @return boolean 返回判断结果，如果满足条件返回true，否则返回false
     */
    public boolean decisionLogic(String matterValue, RuleTreeNodeLineVO nodeLine) {
        // 根据规则限制类型执行不同的比较逻辑
        switch (nodeLine.getRuleLimitType()) {
            case EQUAL:
                // 判断值是否等于规则限制值
                return matterValue.equals(nodeLine.getRuleLimitValue().getCode());
            case GT:
                // 判断值是否大于规则限制值
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) > 0;
            case LT:
                // 判断值是否小于规则限制值
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) < 0;
            case GE:
                // 判断值是否大于等于规则限制值
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) >= 0;
            case LE:
                // 判断值是否小于等于规则限制值
                return matterValue.compareTo(nodeLine.getRuleLimitValue().getCode()) <= 0;
            default:
                // 默认情况下返回false，表示不满足任何条件
                return false;
        }
    }

}

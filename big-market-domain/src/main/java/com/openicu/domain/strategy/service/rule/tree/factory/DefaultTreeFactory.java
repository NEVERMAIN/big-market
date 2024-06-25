package com.openicu.domain.strategy.service.rule.tree.factory;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.model.valobj.RuleTreeVO;
import com.openicu.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.openicu.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.openicu.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description: 规则树工厂
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO){
        return new DecisionTreeEngine(logicTreeNodeGroup,ruleTreeVO);
    }

    /**
     * 决策树动作
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreeActionEntity{
        /** 决策是否通过的值对象 **/
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        /** 结果的值 */
        private StrategyAwardData strategyAwardData;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategyAwardData{

        /** 抽奖奖品ID - 每部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;

    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel{

        /**
         * 奖品锁定
         */
        RULE_LOCK("rule_lock","奖品锁定规则过滤,满足抽奖次数后才能解锁"),

        /**
         * 奖品锁定
         */
        RULE_LUCK_AWARD("rule_luck_award","抽奖n次,对应奖品可解锁抽奖"),

        /**
         * 库存
         */
        RULE_STOCK("rule_stock","库存规则过滤")

        ;


        private final String code;
        private final String info;



        }



}

package com.openicu.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 决策树节点连线
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleTreeNodeLineVO {

    /** 规则树Id */
    private Integer treeId;
    /** 规则 Key节点 From */
    private String ruleNodeFrom;
    /** 规则 Key 节点 To */
    private String ruleNodeTo;
    /** 限定类型 1:= 2:> 3:< 4:>= 5:<= 6:enum */
    private RuleLimitTypeVO ruleLimitType;
    /** 限定值(到下一个节点) */
    private RuleLogicCheckTypeVO ruleLimitValue;

}

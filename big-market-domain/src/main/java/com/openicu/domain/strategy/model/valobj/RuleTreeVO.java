package com.openicu.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description: 决策树的树根
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleTreeVO {

    /** 规则树ID */
    private Integer treeId;
    /** 规则树名称 */
    private String treeName;
    /** 规则树描述 */
    private String treeDesc;
    /** 规则根节点 */
    private String treeRootRuleNode;

    /** 规则节点 */
    private Map<String,RuleTreeNodeVO> treeNodeMap;

}

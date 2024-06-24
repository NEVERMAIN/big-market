package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ruleTreeNodeLine {

    /** 自增 ID */
    private Integer id;
    /** 规则树 Id */
    private Integer treeId;
    /** 规则 key节点From */
    private String ruleNodeFrom;
    /** 规则 key节点To */
    private String ruleNodeTo;
    /** 限定类型 1:= 2:> 3:< 4:>= 5:<= 6:enum */
    private Integer ruleLimitType;
    /** 限定值 */
    private String ruleLimitValue;

}

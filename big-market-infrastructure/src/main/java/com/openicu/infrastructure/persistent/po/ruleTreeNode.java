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
public class ruleTreeNode {

    /** 自增ID */
    private Integer id;
    /** 规则树ID  */
    private Integer treeId;
    /** 规则Key */
    private String ruleKey;
    /** 规则描述 */
    private String ruleDesc;
    /** 规则比值 */
    private String ruleValue;

}

package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 决策树
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ruleTree {

    /** 自增ID **/
    private Integer id;
    /** 规则树ID **/
    private Integer treeId;
    /** 规则树名称 **/
    private String treeName;
    /** 规则树描述 **/
    private String treeDesc;
    /** 规则树根节点 **/
    private String treeRootRuleNode;
    /** 创建时间 **/
    private Date createTime;
    /** 更新时间 **/
    private Date updateTime;

}

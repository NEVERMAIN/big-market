package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/25
 */
@Mapper
public interface IRuleTreeNodeDao {

    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

    /**
     * 根据规则树ID集合查询次数锁的值
     * @param treeIds 规则树ID集合
     * @return
     */
    List<RuleTreeNode> queryRuleLocks(String[] treeIds);
}

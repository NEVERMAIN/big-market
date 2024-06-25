package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/25
 */
@Mapper
public interface IRuleTreeDao {

    RuleTree queryRuleTreeByTreeId(String treeId);

}

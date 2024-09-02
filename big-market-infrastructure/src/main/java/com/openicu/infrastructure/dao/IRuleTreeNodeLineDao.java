package com.openicu.infrastructure.dao;

import com.openicu.infrastructure.dao.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/25
 */
@Mapper
public interface IRuleTreeNodeLineDao {

    /**
     * 根据规则树ID查询规则树节点
     * @param treeId
     * @return
     */
    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);

}

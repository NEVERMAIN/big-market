package com.openicu.infrastructure.dao;

import com.openicu.infrastructure.dao.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 策略表 Dao
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Mapper
public interface IStrategyDao {

    /**
     * 通过策略ID查询策略信息。
     * @param strategyId
     * @return
     */
    Strategy queryStrategyByStrategyId(Long strategyId);



}

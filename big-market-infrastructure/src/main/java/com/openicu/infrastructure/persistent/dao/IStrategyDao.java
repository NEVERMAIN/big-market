package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.Strategy;
import com.openicu.infrastructure.persistent.po.StrategyAward;
import com.openicu.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 策略表 Dao
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Mapper
public interface IStrategyDao {

    List<Strategy> queryStrategyList();


    Strategy queryStrategyByStrategyId(Long strategyId);



}

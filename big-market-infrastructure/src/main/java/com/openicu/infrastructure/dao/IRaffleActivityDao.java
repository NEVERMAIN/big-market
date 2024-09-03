package com.openicu.infrastructure.dao;

import com.openicu.infrastructure.dao.po.RaffleActivity;
import com.openicu.infrastructure.dao.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 抽奖活动配置DAO
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Mapper
public interface IRaffleActivityDao {

    /**
     * 通过活动ID查询活动基础配置
     * @param activityId 活动ID
     * @return 活动基础配置持久化对象
     */
    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

    /**
     * 通过活动ID获得策略ID
     * @param activityId
     * @return
     */
    Long queryStrategyIdByActivityId(Long activityId);

    /**
     * 通过策略ID获得活动ID
     * @param strategyId
     * @return
     */
    Long queryActivityIdByStrategyId(Long strategyId);




}

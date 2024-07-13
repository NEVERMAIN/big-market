package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

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

}

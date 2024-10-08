package com.openicu.infrastructure.dao;

import com.openicu.infrastructure.dao.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Mapper
public interface IRaffleActivityCountDao {

    /**
     * 通过活动次数配置的ID 查询活动次数配置
     * @param activityCountId 活动次数配置的ID
     * @return 活动次数配置持久化对象
     */
    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);


}

package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Mapper
public interface IRaffleActivityDao {

    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

}

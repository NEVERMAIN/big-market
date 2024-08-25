package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.myapp.middleware.db.router.annotation.DBRouterStrategy;
import com.openicu.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 用户中奖记录表DAO
 * @author: 云奇迹
 * @date: 2024/7/16
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserRaffleOrderDao {

    /**
     * 查询未使用的中奖记录
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return
     */
    @DBRouter(key = "userId")
    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrder);

    /**
     * 创建用户抽奖订单
     * @param userRaffleOrder 用户抽奖订单持久化对象
     */
    void insert(UserRaffleOrder userRaffleOrder);

    /**
     * 更新用户抽奖订单状态为已使用
     * @param userRaffleOrder 用户抽奖订单持久化对象
     */
    Integer updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrder);

}

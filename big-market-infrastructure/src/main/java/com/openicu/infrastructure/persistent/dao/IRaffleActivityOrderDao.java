package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.myapp.middleware.db.router.annotation.DBRouterStrategy;
import com.openicu.infrastructure.persistent.po.RaffleActivityOrder;
import com.openicu.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 抽奖活动订单DAO
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IRaffleActivityOrderDao {

    /**
     * 保存抽奖活动订单
     * @param raffleActivityOrder 抽奖活动订单持久化对象
     */
    void insert(RaffleActivityOrder raffleActivityOrder);

    /**
     * 根据用户ID查询抽奖活动订单
     * @param userId 用户唯一ID
     * @return 抽奖活动订单集合
     */
    @DBRouter(key = "userId")
    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);

    /**
     * 查询抽奖活动订单
     * @param raffleActivityOrder
     * @return
     */
    @DBRouter(key = "userId")
    RaffleActivityOrder queryRaffleActivityOrder(RaffleActivityOrder raffleActivityOrder);

    /**
     * 更新订单状态为 completed
     * @param raffleActivityOrderReq
     * @return
     */
    int updateOrderCompleted(RaffleActivityOrder raffleActivityOrderReq);

    /**
     * 查询未支付订单
     * @param raffleActivityOrderReq
     * @return
     */
    @DBRouter(key = "userId")
    RaffleActivityOrder queryUnpaidActivityOrder(RaffleActivityOrder raffleActivityOrderReq);
}

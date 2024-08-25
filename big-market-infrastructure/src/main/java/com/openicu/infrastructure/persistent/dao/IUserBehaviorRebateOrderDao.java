package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.myapp.middleware.db.router.annotation.DBRouterStrategy;
import com.openicu.infrastructure.persistent.po.UserBehaviorRebateOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/28
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserBehaviorRebateOrderDao {

    /**
     * 保存用户行为返利订单
     * @param userBehaviorRebateOrder
     */
    @DBRouter(key = "userId")
    void insert(UserBehaviorRebateOrder userBehaviorRebateOrder);


    /**
     * 根据业务单号查询返利订单
     * @param userBehaviorRebateOrder
     * @return
     */
    @DBRouter(key = "userId")
    List<UserBehaviorRebateOrder> queryOrderByOutBusinessNo(UserBehaviorRebateOrder userBehaviorRebateOrder);
}

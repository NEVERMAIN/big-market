package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.myapp.middleware.db.router.annotation.DBRouterStrategy;
import com.openicu.infrastructure.persistent.po.UserBehaviorRebateOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/28
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserBehaviorRebateOrderDao {

    @DBRouter(key = "userId")
    void insert(UserBehaviorRebateOrder userBehaviorRebateOrder);
}

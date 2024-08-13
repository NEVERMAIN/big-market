package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouterStrategy;
import com.openicu.infrastructure.persistent.po.UserCreditOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserCreditOrderDao {

    /**
     * 保存用户积分订单
     * @param userCreditOrderReq
     */
    void insert(UserCreditOrder userCreditOrderReq);
}

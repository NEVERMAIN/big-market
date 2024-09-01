package com.openicu.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.openicu.infrastructure.dao.po.UserCreditOrder;
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

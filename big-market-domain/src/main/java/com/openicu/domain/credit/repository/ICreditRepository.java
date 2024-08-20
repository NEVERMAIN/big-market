package com.openicu.domain.credit.repository;

import com.openicu.domain.credit.model.aggregate.TradeAggregate;
import com.openicu.domain.credit.model.entity.CreditAccountEntity;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
public interface ICreditRepository {

    /**
     * 保存用户积分订单
     * @param tradeAggregate 交易聚合对象
     */
    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);

    /**
     * 查询用户积分账户
     * @param userId 用户ID
     * @return
     */
    CreditAccountEntity queryUserCreditAccount(String userId);
}

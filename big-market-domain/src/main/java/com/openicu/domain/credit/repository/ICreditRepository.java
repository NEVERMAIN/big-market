package com.openicu.domain.credit.repository;

import com.openicu.domain.credit.model.aggregate.TradeAggregate;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
public interface ICreditRepository {

    /**
     * 保存用户积分订单
     * @param tradeAggregate
     */
    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);

}

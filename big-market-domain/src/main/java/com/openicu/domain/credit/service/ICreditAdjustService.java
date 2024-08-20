package com.openicu.domain.credit.service;

import com.openicu.domain.credit.model.entity.CreditAccountEntity;
import com.openicu.domain.credit.model.entity.TradeEntity;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
public interface ICreditAdjustService {

    /**
     * 创建增加积分额度订单
     * @param tradeEntity 交易实体对象
     * @return
     */
    String createOrder(TradeEntity tradeEntity);

    /**
     * 查询用户积分额度
     * @param userId
     * @return
     */
    CreditAccountEntity queryUserCreditAccount(String userId);
}

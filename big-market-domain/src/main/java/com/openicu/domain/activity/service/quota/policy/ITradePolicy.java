package com.openicu.domain.activity.service.quota.policy;

import com.openicu.domain.activity.model.aggregate.CreateQuotaOrderAggregate;

/**
 * @description: 交易策略接口
 * @author: 云奇迹
 * @date: 2024/8/15
 */
public interface ITradePolicy {

    /**
     * 交易方法
     * @param createQuotaOrderAggregate
     */
    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);

}

package com.openicu.domain.activity.service.quota.policy.impl;

import com.openicu.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.openicu.domain.activity.model.valobj.OrderStateVO;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.stereotype.Component;

/**
 * @description: 积分兑换 - 有支付
 * @author: 云奇迹
 * @date: 2024/8/15
 */
@Component("credit_pay_trade")
public class CreditPayTradePolicy implements ITradePolicy {

    private final IActivityRepository activityRepository;

    public CreditPayTradePolicy (IActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {

        createQuotaOrderAggregate.setOrderState(OrderStateVO.wait_pay);
        activityRepository.doSaveCreditOrder(createQuotaOrderAggregate);

    }
}

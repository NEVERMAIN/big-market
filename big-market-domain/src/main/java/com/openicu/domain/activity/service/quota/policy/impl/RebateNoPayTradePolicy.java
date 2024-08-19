package com.openicu.domain.activity.service.quota.policy.impl;

import com.openicu.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.openicu.domain.activity.model.valobj.OrderStateVO;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @description: 签到返利 - 无支付
 * @author: 云奇迹
 * @date: 2024/8/15
 */
@Component("rebate_no_pay_trade")
public class RebateNoPayTradePolicy implements ITradePolicy {

    private final IActivityRepository activityRepository;

    public RebateNoPayTradePolicy(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {

        // 不需要支付则修改订单金额为0,,状态为完成,直接给用户账户充值
        createQuotaOrderAggregate.setOrderState(OrderStateVO.completed);
        createQuotaOrderAggregate.getActivityOrderEntity().setPayAmount(BigDecimal.ZERO);
        activityRepository.doSaveNoPayOrder(createQuotaOrderAggregate);

    }
}

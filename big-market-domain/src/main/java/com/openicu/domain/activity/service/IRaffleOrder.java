package com.openicu.domain.activity.service;

import com.openicu.domain.activity.model.aggregate.CreateOrderAggregate;
import com.openicu.domain.activity.model.entity.ActivityOrderEntity;
import com.openicu.domain.activity.model.entity.ActivityShopCartEntity;

/**
 * @description: 下单活动接口
 * @author: 云奇迹
 * @date: 2024/6/28
 */
public interface IRaffleOrder {

    /**
     * 以 sku 创建抽奖活动订单,获取参与抽奖资格 (可消耗的次数)
     * @param activityShopCartEntity 活动 sku 实体,通过 sku 领取活动
     * @return 活动参与记录实体
     */
    ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity);


}

package com.openicu.domain.rebate.service;

import com.openicu.domain.rebate.model.entity.BehaviorEntity;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
public interface IBehaviorRebateService {

    /**
     * 创建订单
     * @param behaviorEntity 行为实体对象
     * @return 订单集合
     */
    List<String> createOrder(BehaviorEntity behaviorEntity);

}

package com.openicu.domain.activity.repository;

import com.openicu.domain.activity.model.aggregate.CreateOrderAggregate;
import com.openicu.domain.activity.model.entity.ActivityCountEntity;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @description: 活动仓储接口
 * @author: 云奇迹
 * @date: 2024/6/28
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

}

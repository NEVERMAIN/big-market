package com.openicu.domain.activity.service;

import com.openicu.domain.activity.model.entity.SkuProductEntity;

import java.util.List;

/**
 * @description: sku商品服务接口
 * @author: 云奇迹
 * @date: 2024/8/21
 */
public interface IRaffleActivitySkuProductService {

    /**
     * 根据活动id查询sku商品
     * @param activityId 活动id
     * @return
     */
    List<SkuProductEntity> querySkuProductEntityByActivityId(Long activityId);
}

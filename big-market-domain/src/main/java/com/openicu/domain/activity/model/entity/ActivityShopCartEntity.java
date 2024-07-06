package com.openicu.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 活动购物车实体对象
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityShopCartEntity {

    /** 用户ID */
    private String userId;
    /** 商品SKU activity + activity count */
    private Long sku;

}

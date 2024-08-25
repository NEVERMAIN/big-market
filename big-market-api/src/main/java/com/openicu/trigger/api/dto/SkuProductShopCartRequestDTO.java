package com.openicu.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * @description: 商品购物车请求对象
 * @author: 云奇迹
 * @date: 2024/8/20
 */
@Data
public class SkuProductShopCartRequestDTO implements Serializable {

    /** 用户ID */
    private String userId;
    /** sku 商品 */
    private Long sku;

}

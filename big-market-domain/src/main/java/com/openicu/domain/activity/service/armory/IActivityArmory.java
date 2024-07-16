package com.openicu.domain.activity.service.armory;

/**
 * @description: 活动装配预热接口
 * @author: 云奇迹
 * @date: 2024/7/14
 */
public interface IActivityArmory {

    /**
     * 装撇活动库存
     * @param sku 商品sku
     * @return 是否装配成功
     */
    boolean assembleActivitySku(Long sku);

}

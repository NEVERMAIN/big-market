package com.openicu.domain.activity.service.armory;

import java.util.Date;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/14
 */
public interface IActivityDispatch {

    /**
     * 从缓存中中 扣减活动商品的库存
     * @param sku 商品 sku
     * @param endDateTime 活动截止时间
     * @return 是否扣减成功
     */
    boolean subtractionActivitySkuStock(Long sku, Date endDateTime);
}

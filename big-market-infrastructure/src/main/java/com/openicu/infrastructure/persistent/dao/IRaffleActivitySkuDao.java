package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 活动SKU表 DAO
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Mapper
public interface IRaffleActivitySkuDao {

    /**
     * 根据商品SKU 查询活动SKU对象
     * @param sku 商品sku
     * @return 活动SKU对象
     */
    RaffleActivitySku queryActivitySku(Long sku);

    /**
     * 更新活动 商品SKU库存
     * @param sku 商品 sku
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 清空活动 商品sku库存
     * @param sku 商品sku
     */
    void clearActivitySkuStock(Long sku);
}

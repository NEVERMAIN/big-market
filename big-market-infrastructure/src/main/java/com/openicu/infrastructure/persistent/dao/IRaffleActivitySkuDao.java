package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 通过活动ID查询活动SKU列表
     * @param activityId 活动ID
     * @return
     */
    List<RaffleActivitySku> queryActivitySkuListByActivityId(Long activityId);

    /**
     * 查询活动sku列表
     * @return
     */
    List<RaffleActivitySku> querySkuList();

    /**
     * 查询 activitySku
     * @param activityId 活动ID
     */
    RaffleActivitySku queryActivitySkuByActivityId(Long activityId);
}

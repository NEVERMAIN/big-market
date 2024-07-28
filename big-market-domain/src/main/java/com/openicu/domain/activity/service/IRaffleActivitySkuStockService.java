package com.openicu.domain.activity.service;

import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.List;

/**
 * @description: 活动sku库存处理接口
 * @author: 云奇迹
 * @date: 2024/7/14
 */
public interface IRaffleActivitySkuStockService {

    /**
     * 获取活动sku库存消耗队列
     *
     * @return 奖品库存key信息
     * @throws InterruptedException 异常
     */
    ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException;

    ActivitySkuStockKeyVO takeQueueValue(Long sku) throws InterruptedException;

    /**
     * 清空队列
     */
    void clearQueueValue();

    /**
     * 清空队列 - 指定 sku
     * @param sku
     */
    void clearQueueValue(Long sku);

    /**
     * 延迟队列 + 任务趋势更新活动sku库存
     * @param sku 活动商品sku
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 缓存库存以消耗完毕,清空数据库存
     * @param sku 活动商品sku
     */
    void clearActivitySkuStock(Long sku);

    /**
     * 查询活动sku集合
     * @return
     */
    List<Long> querySkuList();

}

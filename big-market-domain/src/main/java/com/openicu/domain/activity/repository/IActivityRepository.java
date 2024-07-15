package com.openicu.domain.activity.repository;

import com.openicu.domain.activity.model.aggregate.CreateOrderAggregate;
import com.openicu.domain.activity.model.entity.ActivityCountEntity;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.ActivitySkuEntity;
import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;

/**
 * @description: 活动仓储接口
 * @author: 云奇迹
 * @date: 2024/6/28
 */
public interface IActivityRepository {

    /**
     * 根据SKU查询活动商品信息。
     * <p>
     * 该方法用于根据指定的SKU查询与之相关的活动商品实体。这在需要了解特定SKU的活动详情时非常有用，
     * 比如在下订单或者展示商品信息时需要知道商品的活动状态和优惠信息。
     *
     * @param sku 商品的SKU，用于唯一标识一个商品。
     * @return ActivitySkuEntity 活动商品实体，包含SKU相关的活动信息。
     */
    ActivitySkuEntity queryActivitySku(Long sku);

    /**
     * 根据活动ID查询抽奖活动信息。
     * <p>
     * 该方法通过活动ID来获取特定抽奖活动的详细信息。这在需要了解抽奖活动的规则、奖品设置等信息时非常有用，
     * 比如在用户参与抽奖或者展示抽奖活动页面时需要获取这些信息。
     *
     * @param activityId 抽奖活动的ID，用于唯一标识一个抽奖活动。
     * @return ActivityEntity 抽奖活动实体，包含指定ID的抽奖活动的详细信息。
     */
    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    /**
     * 根据活动统计ID查询抽奖活动统计数据。
     * <p>
     * 该方法通过活动统计ID来获取特定抽奖活动的统计数据。这在需要了解抽奖活动的参与情况、中奖情况等统计信息时非常有用，
     * 比如在活动结束后进行数据分析或者展示活动统计页面时需要获取这些信息。
     *
     * @param activityCountId 活动统计的ID，用于唯一标识抽奖活动的统计数据。
     * @return ActivityCountEntity 抽奖活动统计实体，包含指定ID的抽奖活动的统计数据。
     */
    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    /**
     * 保存订单聚合体。
     * <p>
     * 该方法用于保存订单相关的所有信息，包括订单本身、订单项、支付信息等。这是一个非常重要的业务操作，
     * 在用户成功下单时需要调用此方法来持久化订单数据，确保订单信息的准确性和可追溯性。
     *
     * @param createOrderAggregate 待保存的订单聚合体，包含订单相关的所有信息。
     */
    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

    /**
     * 缓存活动商品库存
     * @param cacheKey 缓存Key
     * @param stockCount 商品库存数
     */
    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    /**
     * 扣减商品库存
     * @param sku 商品SKU
     * @param cacheKey 缓存中的Key
     * @param endDateTime 活动截止时间
     * @return 是否扣减成功
     */
    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    /**
     * 扣减商品库存, 发送消息到阻塞队列
     * @param activitySkuStockKeyVO 活动商品库存消息
     */
    void activitySkuStockConsumerSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    /**
     * 从阻塞队列中获取消息
     * @return 活动商品库存消息
     */
    ActivitySkuStockKeyVO takeQueueValue();

    /**
     * 清空阻塞队列中的信息
     */
    void clearQueueValue();

    /**
     * 更新活动商品库存
     * @param sku 商品sku
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 清空 活动商品库存
     * @param sku 商品SKU
     */
    void clearActivitySkuStock(Long sku);


}

package com.openicu.domain.activity.repository;

import com.openicu.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.openicu.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
     * 从阻塞队列中获取消息
     * @param sku 活动sku
     * @return 活动商品库存消息
     */
    ActivitySkuStockKeyVO takeQueueValue(Long sku);

    /**
     * 清空阻塞队列中的信息
     */
    void clearQueueValue();

    /**
     * 清空阻塞队列中的信息
     * @param sku 商品SKU
     */
    void clearQueueValue(Long sku);

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

    /**
     * 查询还没有被消费的订单
     * @param partakeRaffleActivityEntity 参与抽奖活动实体
     * @return UserRaffleOrderEntity 用户抽奖订单实体
     */
    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    /**
     * 查询用户抽奖账户总余额
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return ActivityAccountEntity 用户抽奖总账户实体
     */
    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);

    /**
     * 查询用户抽奖账户月余额
     * @param userId 用户ID
     * @param activityId 活动ID
     * @param month 月份
     * @return ActivityAccountMonthEntity 用户抽奖月账户实体
     */
    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);

    /**
     * 查询用户抽奖账户日余额
     * @param userId 用户ID
     * @param activityId 活动ID
     * @param day 天数
     * @return ActivityAccountDayEntity 用户抽奖日账户实体
     */
    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);

    /**
     * 在一个事务内保存聚合对象
     * @param createPartakeOrderAggregate 领域聚合对象
     */
    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);

    /**
     * 查询活动的sku
     * @param activityId
     * @return
     */
    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);

    /**
     * 查询活动账户 - 日，参与次数
     *
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return 日参与次数
     */
    Integer queryRaffleActivityAccountDayPartakeCount(String userId, Long activityId);

    /**
     * 查询活动sku列表
     * @return
     */
    List<Long> querySkuList();

    /**
     * 查询活动账户
     * @param userId
     * @param activityId
     * @return
     */
    ActivityAccountEntity queryActivityAccountEntity(String userId, Long activityId);

    /**
     * 保存积分支付的订单
     * @param createQuotaOrderAggregate
     */
    void doSaveCreditOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    /**
     * 保存不用支付的订单
     * @param createQuotaOrderAggregate
     */
    void doSaveNoPayOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    /**
     * 更新订单 - 订单状态为 wait_pay
     * @param deliveryOrderEntity
     */
    void updateOrder(DeliveryOrderEntity deliveryOrderEntity);

    /**
     * 查询未支付的订单【一个月内未支付的订单】
     * @param skuRechargeEntity
     * @return
     */
    UnpaidActivityOrderEntity queryUnpaidActivityOrder(SkuRechargeEntity skuRechargeEntity);

    /**
     * 查询活动商品列表
     * @param activityId 活动ID
     * @return
     */
    List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId);

    /**
     * 查询用户积分账户余额
     * @param userId
     * @return
     */
    BigDecimal queryUserCreditAccountAmount(String userId);
}

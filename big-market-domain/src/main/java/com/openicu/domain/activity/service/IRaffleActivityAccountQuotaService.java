package com.openicu.domain.activity.service;

import com.openicu.domain.activity.model.entity.ActivityAccountEntity;
import com.openicu.domain.activity.model.entity.DeliveryOrderEntity;
import com.openicu.domain.activity.model.entity.SkuRechargeEntity;
import com.openicu.domain.activity.model.entity.UnpaidActivityOrderEntity;

/**
 * @description: 抽奖额度扣减服务
 * @author: 云奇迹
 * @date: 2024/6/28
 */
public interface IRaffleActivityAccountQuotaService {

    /**
     * 创建 sku 账户充值订单,给用户增加抽奖次数
     * <p>
     *     1. 在【打卡,签到,分享,对话,积分兑换】等行为动作下,创建出互动共订单,给用户的活动账户【日、月】充值可用的抽奖次数
     *     2. 对于用户可获得的抽奖次数,比如首次进来就有一次,则是依赖于运行配置的动作,在前端页面上。用户点击后,可以获得一次抽奖次数
     * </p>
     * @param skuRechargeEntity  活动商品充值实体对象
     * @return 未支付订单
     */
    UnpaidActivityOrderEntity createOrder(SkuRechargeEntity skuRechargeEntity);

    /**
     * 查询活动账户 - 日，参与次数
     *
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return
     */
    Integer queryRaffleActivityAccountDayPartakeCount(String userId, Long activityId);

    /**
     * 查询活动账户 - 总
     * @param userId
     * @param activityId
     * @return
     */
    ActivityAccountEntity queryActivityAccountEntity(String userId, Long activityId);

    /**
     * 订单出货 - 积分充值
     * @param deliveryOrderEntity
     */
    void updateOrder(DeliveryOrderEntity deliveryOrderEntity);

}

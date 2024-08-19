package com.openicu.domain.activity.service.quota;

import com.openicu.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.openicu.domain.activity.service.quota.policy.ITradePolicy;
import com.openicu.domain.activity.service.quota.rule.IActionChain;
import com.openicu.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @description: 抽奖活动抽象类，定义标准的流程
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Slf4j
public abstract class AbstractRaffleActivityAccountQuota extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {

    /** 同类型的交易策略实现类，通过构造函数注入到 Map 中 */
    private final Map<String, ITradePolicy> tradePolicyMap;

    public AbstractRaffleActivityAccountQuota(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory, Map<String, ITradePolicy> tradePolicyMap) {
        super(activityRepository, defaultActivityChainFactory);
        this.tradePolicyMap = tradePolicyMap;
    }


    @Override
    public String createOrder(SkuRechargeEntity skuRechargeEntity) {

        // 1.参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.查询基础信息
        // 2.1. 通过 sku 查询活动信息
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        // 2.2. 查询活动信息
        ActivityEntity activityEntity =
                queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 2.3. 查询次数信息(用户在活动上可参与的次数)
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 3. 活动动作规则校验【过滤失败则直接抛异常】
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 4. 构建订单聚合对象
        CreateQuotaOrderAggregate createOrderAggregate =
                buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);

        // 5. 交易策略 -【积分兑换,支付类订单】【返利无支付交易订单,直接充值到账】【订单状态变更交易类型策略】
        ITradePolicy tradePolicy = tradePolicyMap.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createOrderAggregate);

        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }

    /**
     * 创建订单聚合体的抽象方法。
     *
     * @param skuRechargeEntity 充值商品实体，包含充值相关的详细信息。
     * @param activitySkuEntity 活动商品实体，描述活动的具体商品信息。
     * @param activityEntity 活动实体，包含活动的详细配置和规则。
     * @param activityCountEntity 活动计数实体，记录活动的参与次数等统计信息。
     * @return 返回创建的订单聚合体，用于后续的订单保存操作。
     */
    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);


}

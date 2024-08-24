package com.openicu.domain.activity.service.quota;

import com.openicu.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.model.valobj.OrderTradeTypeVO;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.openicu.domain.activity.service.quota.policy.ITradePolicy;
import com.openicu.domain.activity.service.quota.rule.IActionChain;
import com.openicu.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
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
    public UnpaidActivityOrderEntity createOrder(SkuRechargeEntity skuRechargeEntity) {

        // 1.参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.查询未支付订单【一个月以内的未支付订单】
        UnpaidActivityOrderEntity unpaidCreditOrder = activityRepository.queryUnpaidActivityOrder(skuRechargeEntity);
        if (null != unpaidCreditOrder) return unpaidCreditOrder;

        // 3.查询基础信息
        // 3.1. 通过 sku 查询活动信息
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        // 3.2. 查询活动信息
        ActivityEntity activityEntity =
                queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3.3. 查询次数信息(用户在活动上可参与的次数)
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 4.账户额度【交易属性的兑换,需要校验额度账户】
        if(OrderTradeTypeVO.credit_pay_trade.equals(skuRechargeEntity.getOrderTradeType())){
            BigDecimal availableAmount = activityRepository.queryUserCreditAccountAmount(userId);
            if(availableAmount.compareTo(activitySkuEntity.getProductAmount()) < 0){
                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_ACCOUNT.getCode(),
                        ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_ACCOUNT.getInfo());
            }

        }

        // 5. 活动动作规则校验【过滤失败则直接抛异常】
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 6. 构建订单聚合对象
        CreateQuotaOrderAggregate createOrderAggregate =
                buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);

        // 7. 交易策略 -【积分兑换,支付类订单】【返利无支付交易订单,直接充值到账】【订单状态变更交易类型策略】
        ITradePolicy tradePolicy = tradePolicyMap.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createOrderAggregate);

        // 8. 返回订单信息
        ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
        return UnpaidActivityOrderEntity.builder()
                .userId(activityOrderEntity.getUserId())
                .orderId(activityOrderEntity.getOrderId())
                .outBusinessNo(activityOrderEntity.getOutBusinessNo())
                .payAmount(activityOrderEntity.getPayAmount())
                .build();

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

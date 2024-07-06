package com.openicu.domain.activity.service;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * @description: 抽奖活动抽象类，定义标准的流程
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Slf4j
public abstract class AbstractRaffleActivity implements IRaffleOrder {

    protected IActivityRepository activityRepository;

    public AbstractRaffleActivity(IActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {

        // 1.通过 sku 查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        // todo 对互动库存的校验
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // todo 对活动有效期的校验
        // 3.查询次数信息 (用户在活动上可参与的次数)
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        // todo 校验用户参与活动的次数
        log.info("查询结果: activitySkuEntity:{} activityEntity:{} activityCountEntity:{}", JSON.toJSONString(activitySkuEntity),JSON.toJSONString(activityEntity),JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder()
                .userId(activityShopCartEntity.getUserId())
                .activityId(activityEntity.getActivityId())
                .activityName(activityEntity.getActivityName())
                .strategyId(activityEntity.getStrategyId())
                .orderId(RandomStringUtils.randomNumeric(12))
                .orderTime(new Date())
                .totalCount(activityCountEntity.getTotalCount())
                .monthCount(activityCountEntity.getMonthCount())
                .dayCount(activityCountEntity.getDayCount())
                .build();
    }
}

package com.openicu.domain.activity.service.quota;

import com.openicu.domain.activity.model.entity.ActivityCountEntity;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.ActivitySkuEntity;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;

/**
 * @description: 抽奖活动的支撑类
 * @author: 云奇迹
 * @date: 2024/7/9
 */
public class RaffleActivityAccountQuotaSupport {

    protected DefaultActivityChainFactory defaultActivityChainFactory;

    protected IActivityRepository activityRepository;

    public RaffleActivityAccountQuotaSupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory){
        this.defaultActivityChainFactory = defaultActivityChainFactory;
        this.activityRepository = activityRepository;
    }


    public ActivitySkuEntity queryActivitySku(Long sku){
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId){
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId){
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }



}

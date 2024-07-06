package com.openicu.domain.activity.service;

import com.openicu.domain.activity.model.aggregate.CreateOrderAggregate;
import com.openicu.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 抽奖活动服务
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity{

    @Resource
    private IActivityRepository activityRepository;


    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}

package com.openicu.domain.strategy.service;

import com.openicu.domain.strategy.model.entity.ActivityAwardEntity;
import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @description: 抽奖奖品相关接口
 * @author: 云奇迹
 * @date: 2024/6/26
 */
public interface IRaffleAward {

    /**
     * 根据策略ID查询奖品列表配置
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

    /**
     * 查询活动奖品接口
     * @param activityId
     * @return
     */
    List<ActivityAwardEntity> queryActivityAwardList(Long activityId,String userId);
}

package com.openicu.domain.strategy.service;

import com.openicu.domain.strategy.model.entity.RaffleAwardEntity;
import com.openicu.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @description: 抽奖策略接口
 * @author: 云奇迹
 * @date: 2024/6/19
 */
public interface IRaffleStrategy {

    /**
     * 执行抽奖逻辑并返回抽奖结果。
     *
     * @param raffleFactorEntity 抽奖因子实体对象，包含了影响抽奖结果的各种因素，如抽奖次数、用户信息等。
     * @return RaffleAwardEntity 抽奖结果实体对象，包含了中奖信息，如奖品名称、奖品描述等。
     */
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}

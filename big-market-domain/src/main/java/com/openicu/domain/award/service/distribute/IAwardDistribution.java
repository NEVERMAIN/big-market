package com.openicu.domain.award.service.distribute;

import com.openicu.domain.award.model.aggregate.UserAwardDistributionAggregate;

/**
 * @description: 奖品配送接口
 * @author: 云奇迹
 * @date: 2024/7/20
 */
public interface IAwardDistribution {

    /**
     * 奖品配送接口,奖品类型:【user_credit_random,openai_use_count,openai_model】
     * @param userAwardDistributionAggregate 奖品发送聚合对象
     * @return
     */
    Boolean doDistribution(UserAwardDistributionAggregate userAwardDistributionAggregate);


}

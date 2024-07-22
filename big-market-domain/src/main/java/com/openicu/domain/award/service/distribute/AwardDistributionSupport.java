package com.openicu.domain.award.service.distribute;

import com.openicu.domain.award.model.aggregate.UserAwardDistributionAggregate;
import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import com.openicu.domain.award.reposiotry.IAwardRepository;

/**
 * @description: 发奖抽奖类
 * @author: 云奇迹
 * @date: 2024/7/20
 */
public  class AwardDistributionSupport {

    private IAwardRepository awardRepository;

    public AwardDistributionSupport(IAwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    public void updateAwardDistributionCompleted(String orderId,String userId) {
        awardRepository.updateUserAwardRecordCompleted(orderId,userId);
    }

}

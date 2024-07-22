package com.openicu.domain.award.service.distribute;

import com.openicu.domain.award.reposiotry.IAwardRepository;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/20
 */
public abstract class AbstractAwardDistribution extends AwardDistributionSupport implements IAwardDistribution{

    public AbstractAwardDistribution(IAwardRepository awardRepository) {
        super(awardRepository);
    }
}

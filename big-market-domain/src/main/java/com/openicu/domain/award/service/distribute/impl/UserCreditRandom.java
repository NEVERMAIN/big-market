package com.openicu.domain.award.service.distribute.impl;

import com.openicu.domain.award.model.aggregate.UserAwardDistributionAggregate;
import com.openicu.domain.award.model.entity.AwardEntity;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.domain.award.service.distribute.AbstractAwardDistribution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 用户随机积分
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Slf4j
@Component("user_credit_random")
public class UserCreditRandom extends AbstractAwardDistribution {

    public UserCreditRandom(IAwardRepository awardRepository) {
        super(awardRepository);
    }

    @Override
    public Boolean doDistribution(UserAwardDistributionAggregate userAwardDistributionAggregate) {

        String userId = userAwardDistributionAggregate.getUserId();
        AwardEntity awardEntity = userAwardDistributionAggregate.getAwardEntity();
        String orderId = userAwardDistributionAggregate.getOrderId();

        log.info("模拟调用openai服务增加用户随机积分");

        // 更新用户领奖结果
        super.updateAwardDistributionCompleted(orderId,userId);


        return true;
    }
}

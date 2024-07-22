package com.openicu.domain.award.service.distribute.impl;

import com.openicu.domain.award.model.aggregate.UserAwardDistributionAggregate;
import com.openicu.domain.award.model.entity.AwardEntity;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.domain.award.service.distribute.AbstractAwardDistribution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: OpenAI 服务模型
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Component("openai_model")
@Slf4j
public class OpenaiModel extends AbstractAwardDistribution {

    public OpenaiModel(IAwardRepository awardRepository) {
        super(awardRepository);
    }

    @Override
    public Boolean doDistribution(UserAwardDistributionAggregate userAwardDistributionAggregate) {

        String userId = userAwardDistributionAggregate.getUserId();
        AwardEntity awardEntity = userAwardDistributionAggregate.getAwardEntity();
        String orderId = userAwardDistributionAggregate.getOrderId();

        log.info("模拟调用openai服务增加openai模型 ");

        // 更新用户领奖结果
        super.updateAwardDistributionCompleted(orderId,userId);


        return true;
    }
}

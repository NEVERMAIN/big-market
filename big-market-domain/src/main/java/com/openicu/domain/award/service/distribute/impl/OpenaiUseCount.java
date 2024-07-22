package com.openicu.domain.award.service.distribute.impl;

import com.openicu.domain.award.model.aggregate.UserAwardDistributionAggregate;
import com.openicu.domain.award.model.entity.AwardEntity;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.domain.award.service.distribute.AbstractAwardDistribution;
import com.openicu.domain.award.service.distribute.IAwardDistribution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: OpenAI 服务使用次数
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Slf4j
@Component("openai_use_count")
public class OpenaiUseCount extends AbstractAwardDistribution {

    public OpenaiUseCount(IAwardRepository awardRepository) {
        super(awardRepository);
    }

    @Override
    public Boolean doDistribution(UserAwardDistributionAggregate userAwardDistributionAggregate) {
        String userId = userAwardDistributionAggregate.getUserId();
        AwardEntity awardEntity = userAwardDistributionAggregate.getAwardEntity();
        String orderId = userAwardDistributionAggregate.getOrderId();

        log.info("模拟调用openai服务增加OpenAI 服务使用次数 ");

        // 更新用户领奖结果
        super.updateAwardDistributionCompleted(orderId,userId);

        return true;
    }
}

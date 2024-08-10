package com.openicu.domain.award.service.distribute.impl;

import com.openicu.domain.award.model.aggregate.GiveOutPrizesAggregate;
import com.openicu.domain.award.model.entity.DistributeAwardEntity;
import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import com.openicu.domain.award.model.entity.UserCreditAwardEntity;
import com.openicu.domain.award.model.valobj.AwardStateVO;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.domain.award.service.distribute.IDistributeAward;
import com.openicu.types.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/9
 */
@Component("user_credit_random")
public class UserCreditRandomAward implements IDistributeAward {

    @Resource
    private IAwardRepository awardRepository;

    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) {

        Integer awardId = distributeAwardEntity.getAwardId();
        String awardConfig = distributeAwardEntity.getAwardConfig();
        if (StringUtils.isBlank(awardConfig)) {
            awardConfig = awardRepository.queryAwardConfig(awardId);
        }

        String[] creditRange = awardConfig.split(Constants.SPLIT);
        if (creditRange.length != 2) {
            throw new RuntimeException("award_config [" + awardConfig + "] 配置不是一个范围值,如 1,100");
        }

        // 生成随机积分值
        BigDecimal creditAmount = generateRandom(new BigDecimal(creditRange[0]), new BigDecimal(creditRange[1]));

        // 构建聚合对象
        UserAwardRecordEntity userAwardRecordEntity = GiveOutPrizesAggregate.buildDistributeUserAwardRecordEntity(
                distributeAwardEntity.getUserId(),
                distributeAwardEntity.getOrderId(),
                distributeAwardEntity.getAwardId(),
                AwardStateVO.complete
        );

        UserCreditAwardEntity userCreditAwardEntity = GiveOutPrizesAggregate.buildUserCreditAwardEntity(
                distributeAwardEntity.getUserId(), creditAmount);

        GiveOutPrizesAggregate giveOutPrizesAggregate = new GiveOutPrizesAggregate();
        giveOutPrizesAggregate.setUserId(userCreditAwardEntity.getUserId());
        giveOutPrizesAggregate.setUserAwardRecordEntity(userAwardRecordEntity);
        giveOutPrizesAggregate.setUserCreditAwardEntity(userCreditAwardEntity);

        // 存储发奖对象
        awardRepository.saveGiveOutPrizesAggregate(giveOutPrizesAggregate);

    }


    private BigDecimal generateRandom(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(BigDecimal.valueOf(Math.random())).multiply(max.subtract(min));
        return randomBigDecimal.round(new MathContext(3));
    }

}

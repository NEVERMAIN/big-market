package com.openicu.domain.award.service.distribute.factory;

import com.openicu.domain.award.service.distribute.IAwardDistribution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 奖品分发工厂
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Component
public class AwardDistributionFactory {

    private final Map<String,IAwardDistribution> awardDistributionGroup;

    public AwardDistributionFactory(Map<String,IAwardDistribution> awardDistributionGroup){
        this.awardDistributionGroup = awardDistributionGroup;
    }

    public IAwardDistribution getAwardDistribution(String key){
        return awardDistributionGroup.get(key);
    }


    @Getter
    @AllArgsConstructor
    public enum AwardModel {

        // 奖品类型:【user_credit_random,openai_use_count,openai_model】
        USER_CREDIT_RANDOM("user_credit_random","用户随机积分"),
        OPENAI_USE_COUNT("openai_use_count","openai用户次数"),
        OPENAI_MODEL("openai_model","openai模型"),

        ;

        private String code;
        private String info;

    }

}

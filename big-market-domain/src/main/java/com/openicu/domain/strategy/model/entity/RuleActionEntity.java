package com.openicu.domain.strategy.model.entity;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @description: 规则动作实体
 * @author: 云奇迹
 * @date: 2024/6/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity> {

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();

    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    /** 规则模型 */
    private String ruleModel;

    private T data;


    static public class RaffleEntity {

    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class RaffleBeforeEntity extends RaffleEntity {
        /** 策略 ID */
        private Long strategyId;
        /** 权重值 Key: 用于抽奖时可以选择权重抽奖 */
        private String ruleWeightValueKey;
        /** 奖品 ID */
        private Integer awardId;

    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class RaffleCenterEntity extends RaffleEntity {
        /** 策略 ID */
        private Long strategyId;
        /** 权重值 Key: 用于抽奖时可以选择权重抽奖 */
        private String ruleWeightValueKey;
        /** 奖品 ID */
        private Integer awardId;

    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class RaffleAfterEntity extends RaffleEntity {
        /** 策略 ID */
        private Long strategyId;
        /** 权重值 Key: 用于抽奖时可以选择权重抽奖 */
        private String ruleWeightValueKey;
        /** 奖品 ID */
        private Integer awardId;
    }


}

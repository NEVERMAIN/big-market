package com.openicu.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAwardEntity {

    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID-内部流转使用 */
    private Integer awardId;
    /** 抽奖奖品标题 */
    private String awardTitle;
    /** 抽奖奖品副标题 */
    private String awardSubtitle;
    /** 奖品库存总量 */
    private Integer awardCount;
    /** 奖品库存剩余 */
    private Integer awardCountSurplus;
    /** 奖品中奖概率 */
    private BigDecimal awardRate;

    /** 抽象规则类型；1-策略规则、2-奖品规则 */
    private Integer ruleType;
    /** 抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】 */
    private String ruleModel;
    /** 抽奖规则比值 */
    private String ruleValue;
    /** 排序 */
    private Integer sort;
    /** 是否被锁定 */
    private boolean lock;

    public void isAwardLock(Integer userRaffleCount) {

        if ("rule_lock".equals(ruleModel)) {
            lock = userRaffleCount >= Integer.parseInt(ruleValue);
        }

    }


}

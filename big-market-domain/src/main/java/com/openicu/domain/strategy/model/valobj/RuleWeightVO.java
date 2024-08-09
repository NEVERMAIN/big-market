package com.openicu.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:  权重规则值对象
 * @author: 云奇迹
 * @date: 2024/8/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleWeightVO {

    /** 原始规则配置值 */
    private String ruleValue;
    /** 权重值 */
    private Integer weight;
    /** 奖品配置 */
    private List<Integer> awardIds;
    /** 奖品列表 */
    private List<Award> awardList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Award{

        private Integer awardId;

        private String awardTitle;

    }


}

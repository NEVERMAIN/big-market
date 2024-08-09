package com.openicu.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 抽奖策略规则，权重配置，查询N次抽奖可解锁奖品范围，应答对象
 * @author: 云奇迹
 * @date: 2024/8/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategyRuleWeightResponseDTO {

    private Integer ruleWeightCount;

    private Integer userActivityAccountTotalUserCount;

    private List<StrategyAward> strategyAwardList;


    @Data
    public static class StrategyAward {

        /** 奖品ID */
        private Integer awardId;
        /** 奖品标题 */
        private String awardTitle;

    }


}

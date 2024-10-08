package com.openicu.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 抽奖应答结果
 * @author: 云奇迹
 * @date: 2024/6/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategyResponseDTO implements Serializable {

    /** 奖品ID */
    private Integer awardId;
    /** 排序编号【策略奖品配置的奖品顺序编号】 */
    private Integer awardIndex;

}

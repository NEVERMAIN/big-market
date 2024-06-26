package com.openicu.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 抽奖奖品列表，应答对象
 * @author: 云奇迹
 * @date: 2024/6/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListResponseDTO {

    /**奖品ID*/
    private Integer awardId;
    /** 奖品标题 */
    private String awardTitle;
    /** 奖品副标题【抽奖1次后解锁】 */
    private String awardSubtitle;
    /** 排序编号 */
    private Integer sort;

}


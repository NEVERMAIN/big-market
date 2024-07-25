package com.openicu.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAwardListResponseDTO {

    /**奖品ID*/
    private Integer awardId;
    /** 奖品标题 */
    private String awardTitle;
    /** 奖品副标题【抽奖1次后解锁】 */
    private String awardSubtitle;
    /** 排序编号 */
    private Integer sort;
    /** 是否被锁 */
    private Boolean lock;


}

package com.openicu.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDrawResponseDTO implements Serializable {

    /** 奖品ID */
    private Integer awardId;
    /** 奖品标题 */
    private String awardTitle;
    /** 排序编号【策略奖品配置的奖品顺序编号】 */
    private Integer awardIndex;

}

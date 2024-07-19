package com.openicu.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 活动账户月额度 实体对象
 * @author: 云奇迹
 * @date: 2024/7/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAccountMonthEntity {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;
    /** 月(yyyy-mm) */
    private String month;
    /** 月次数 */
    private Integer monthCount;
    /** 月次数-剩余 */
    private Integer monthCountSurplus;

}

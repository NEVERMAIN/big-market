package com.openicu.trigger.api.dto;

import lombok.Data;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/23
 */
@Data
public class ActivityDrawRequestDTO {
    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;

}

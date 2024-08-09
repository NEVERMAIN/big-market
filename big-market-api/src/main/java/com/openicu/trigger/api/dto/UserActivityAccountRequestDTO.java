package com.openicu.trigger.api.dto;

import lombok.Data;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/7
 */
@Data
public class UserActivityAccountRequestDTO {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;


}

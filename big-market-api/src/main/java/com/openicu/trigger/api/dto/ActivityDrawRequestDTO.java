package com.openicu.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 活动抽奖请求
 * @author: 云奇迹
 * @date: 2024/7/23
 */
@Data
public class ActivityDrawRequestDTO implements Serializable {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;

}

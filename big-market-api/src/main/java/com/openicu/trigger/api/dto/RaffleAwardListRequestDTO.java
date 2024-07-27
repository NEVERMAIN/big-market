package com.openicu.trigger.api.dto;

import lombok.Data;

/**
 * @description: 抽奖奖品列表，请求对象
 * @author: 云奇迹
 * @date: 2024/6/26
 */
@Data
public class RaffleAwardListRequestDTO {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;

}


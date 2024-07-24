package com.openicu.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartakeRaffleActivityEntity {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;


}

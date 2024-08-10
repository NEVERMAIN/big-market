package com.openicu.domain.award.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributeAwardEntity {

    /** 用户ID */
    private String userId;
    /** 订单ID */
    private String orderId;
    /** 奖品ID */
    private Integer awardId;
    /** 奖品配置 */
    private String awardConfig;


}

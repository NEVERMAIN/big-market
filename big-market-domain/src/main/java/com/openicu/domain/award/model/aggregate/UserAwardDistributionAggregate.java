package com.openicu.domain.award.model.aggregate;

import com.openicu.domain.award.model.entity.AwardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAwardDistributionAggregate {
    /** 用户ID */
    private String userId;
    /** 订单ID */
    private String orderId;
    /** 奖品信息 */
    private AwardEntity awardEntity;

}

package com.openicu.domain.activity.model.aggregate;

import com.openicu.domain.activity.model.entity.ActivityAccountEntity;
import com.openicu.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderAggregate {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;
    /** 增加:总次数 */
    private Integer totalCount;
    /** 增加:日次数 */
    private Integer dayCount;
    /** 增加:月次数 */
    private Integer monthCount;
    /** 活动订单实体 */
    private ActivityOrderEntity activityOrderEntity;

}

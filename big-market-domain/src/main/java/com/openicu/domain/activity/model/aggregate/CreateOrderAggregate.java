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

    /** 活动账户实体 */
    private ActivityAccountEntity activityAccountEntity;
    /** 活动订单实体 */
    private ActivityOrderEntity activityOrderEntity;

}

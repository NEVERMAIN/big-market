package com.openicu.domain.rebate.model.aggregate;

import com.openicu.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.openicu.domain.rebate.model.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorRebateAggregate {

    /** 用户ID */
    private String userId;
    /** 行为返利订单实体对象 */
    private BehaviorRebateOrderEntity behaviorRebateOrderEntity;
    /** 任务实体对象 */
    private TaskEntity  taskEntity;


}

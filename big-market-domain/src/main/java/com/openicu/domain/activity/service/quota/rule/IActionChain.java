package com.openicu.domain.activity.service.quota.rule;

import com.openicu.domain.activity.model.entity.ActivityCountEntity;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/9
 */
public interface IActionChain extends IActionChainArmory{


    /**
     * 执行特定动作的方法，该动作涉及活动SKU、活动和活动计数实体。
     *
     * @param activitySkuEntity 活动SKU实体，包含活动的具体SKU信息。可能用于判断活动的可用性或特定SKU的库存情况。
     * @param activityEntity 活动实体，包含活动的详细信息。可能用于验证活动的有效性或获取活动规则。
     * @param activityCountEntity 活动计数实体，可能包含与活动相关的统计信息。例如，参与活动的用户数量或活动的总销量。
     * @return boolean 返回一个布尔值，表示动作是否成功执行。具体含义由实现决定，通常表示操作是否成功或满足某些条件。
     */
    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

}

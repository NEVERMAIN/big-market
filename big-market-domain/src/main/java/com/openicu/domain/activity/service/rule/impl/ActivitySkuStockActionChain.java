package com.openicu.domain.activity.service.rule.impl;

import com.openicu.domain.activity.model.entity.ActivityCountEntity;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.ActivitySkuEntity;
import com.openicu.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/9
 */
@Slf4j
@Component("activity_sku_action_chain")
public class ActivitySkuStockActionChain extends AbstractActionChain {

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {

        log.info("活动责任链-商品库存处理【校验&扣减】开始.....");

        Integer stockCountSurplus = activitySkuEntity.getStockCountSurplus();
        if(stockCountSurplus <= 0){
            return false;
        }

        return next().action(activitySkuEntity,activityEntity,activityCountEntity);
    }
}

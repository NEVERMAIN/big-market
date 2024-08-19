package com.openicu.domain.activity.service.quota.rule.impl;

import com.openicu.domain.activity.model.entity.ActivityCountEntity;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.ActivitySkuEntity;
import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.armory.IActivityDispatch;
import com.openicu.domain.activity.service.quota.rule.AbstractActionChain;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 活动商品库存处理
 * @author: 云奇迹
 * @date: 2024/7/9
 */
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {

    @Resource
    private IActivityDispatch activityDispatch;

    @Resource
    private IActivityRepository activityRepository;

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {

        log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】开始。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        // 1.扣减库存
        boolean status = activityDispatch.subtractionActivitySkuStock(activitySkuEntity.getSku(), activityEntity.getEndDateTime());
        // true 库存扣减成功
        if(status){
            log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】成功。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());

            // 2. 写入延迟队列,延迟消费更新库存记录
            activityRepository.activitySkuStockConsumerSendQueue(ActivitySkuStockKeyVO.builder()
                    .sku(activitySkuEntity.getSku())
                    .activityId(activityEntity.getActivityId())
                    .build());

            // 3.返回正确结果
            return true;
        }

        // 4.false 库存扣减失败
        throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());

    }
}

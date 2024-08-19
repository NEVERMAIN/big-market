package com.openicu.trigger.job;

import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.openicu.domain.activity.service.IRaffleActivitySkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/14
 */
@Slf4j
@Component
public class UpdateActivitySkuStockJob {

    @Resource
    private IRaffleActivitySkuStockService skuStock;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {

            List<Long> skuList = skuStock.querySkuList();
            if(skuList.isEmpty()){
                return;
            }

            for (Long sku : skuList) {
                ActivitySkuStockKeyVO activitySkuStockKeyVO = skuStock.takeQueueValue(sku);
                if (null == activitySkuStockKeyVO) return;
                log.info("定时任务，更新活动sku库存 sku:{} activityId:{}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
                skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
            }

        } catch (Exception e) {
            log.error("定时任务,更新活动sku库存失败", e);
        }

    }
}

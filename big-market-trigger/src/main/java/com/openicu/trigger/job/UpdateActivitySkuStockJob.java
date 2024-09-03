package com.openicu.trigger.job;

import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.openicu.domain.activity.service.IRaffleActivitySkuStockService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private ThreadPoolExecutor executor;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 本地化注解:  @Scheduled(cron = "0/5 * * * * ?")
     * 分布式注解:  @XxlJob("UpdateActivitySkuStockJob")
     */
    @XxlJob("UpdateActivitySkuStockJob")
    public void exec() {
        RLock lock = redissonClient.getLock("big-market-UpdateActivitySkuStockJob");
        boolean isLock = false;
        try {
            // 1.抢占锁执行定时任务
            isLock = lock.tryLock(3, 0, TimeUnit.SECONDS);
            if (!isLock) return;

            List<Long> skuList = skuStock.querySkuList();
            if (skuList.isEmpty()) {
                return;
            }

            for (Long sku : skuList) {
                executor.execute(() -> {
                    ActivitySkuStockKeyVO activitySkuStockKeyVO = null;
                    try {
                        activitySkuStockKeyVO = skuStock.takeQueueValue(sku);
                    } catch (InterruptedException e) {
                        log.error("定时任务，更新活动sku库存失败 sku: {} topic: {}", sku, activitySkuStockKeyVO.getActivityId());
                    }
                    if (null == activitySkuStockKeyVO) return;
                    log.info("定时任务，更新活动sku库存 sku:{} activityId:{}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
                    skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
                });
            }

        } catch (Exception e) {
            log.error("定时任务,更新活动sku库存失败", e);
        } finally {
            if (isLock) {
                lock.unlock();
            }
        }

    }
}

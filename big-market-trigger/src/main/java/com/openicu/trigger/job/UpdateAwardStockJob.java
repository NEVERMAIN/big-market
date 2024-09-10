package com.openicu.trigger.job;

import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.openicu.domain.strategy.service.IRaffleAward;
import com.openicu.domain.strategy.service.IRaffleStock;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 定时任务消费对列
 * @author: 云奇迹
 * @date: 2024/6/25
 */
@Slf4j
@Component
public class UpdateAwardStockJob {

    @Resource
    private IRaffleStock raffleStock;

    @Resource
    private ThreadPoolExecutor executor;

    @Resource
    private IRaffleAward raffleAward;

    @Resource
    private RedissonClient redissonClient;


    /**
     * 本地注解: @Scheduled(cron = "0/5 * * * * ?")
     * 分布式注解: @XxlJob("UpdateAwardStockJob")
     */
    @Timed(value = "UpdateAwardStockJob",description = "更新奖品库存任务")
    @XxlJob("UpdateAwardStockJob")
    public void exec() {

        RLock lock = redissonClient.getLock("big-market-UpdateAwardStockJob");
        boolean isLock = false;

        try {

            // 0.抢占分布式锁
            isLock = lock.tryLock(3, 0, TimeUnit.SECONDS);
            if (!isLock) return;
            // 1.查询活动策略奖品集合
            List<StrategyAwardStockKeyVO> strategyAwardStockKeyVOS = raffleAward.queryOpenActivityStrategyAwardList();
            if (null == strategyAwardStockKeyVOS) {
                return;
            }
            for (StrategyAwardStockKeyVO strategyAwardStockKeyVO : strategyAwardStockKeyVOS) {
                executor.execute(() -> {

                    StrategyAwardStockKeyVO queueStrategyAwardStockKeyVO = null;
                    try {

                        queueStrategyAwardStockKeyVO = raffleStock.takeQueueValue(strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
                        if (null == queueStrategyAwardStockKeyVO) return;
                        log.info("定时任务，更新奖品消耗库存 strategyId:{} awardId:{}", queueStrategyAwardStockKeyVO.getStrategyId(), queueStrategyAwardStockKeyVO.getAwardId());
                        raffleStock.updateStrategyAwardStock(queueStrategyAwardStockKeyVO.getStrategyId(), queueStrategyAwardStockKeyVO.getAwardId());

                    } catch (InterruptedException e) {

                        log.error("定时任务，更新奖品消耗库存失败 strategyId:{} awardId:{}", strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
                    }

                });
            }

        } catch (Exception e) {
            log.error("定时任务,更新奖品消耗库存失败", e);
        } finally {
            if (isLock) {
                lock.unlock();
            }
        }
    }
}

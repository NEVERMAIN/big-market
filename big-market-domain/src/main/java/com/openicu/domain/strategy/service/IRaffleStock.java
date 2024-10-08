package com.openicu.domain.strategy.service;

import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * @description: 抽奖库存相关服务，获取库存消耗队列
 * @author: 云奇迹
 * @date: 2024/6/25
 */
public interface IRaffleStock {

    /**
     * 获取奖品库存消耗队列
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 异常
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 根据策略ID+奖品ID获取奖品库存消耗队列
     * @param strategyId
     * @param awardId
     * @return
     * @throws InterruptedException
     */
    StrategyAwardStockKeyVO takeQueueValue(Long strategyId,Integer awardId) throws InterruptedException;

    /**
     * 更新奖品库存消耗记录
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    void updateStrategyAwardStock(Long strategyId,Integer awardId);

    /**
     * 清空队列
     */
    void clearQueueValue();

    /**
     * 清空抽奖奖品库存
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     */
    void clearStrategyAwardStock(Long strategyId,Integer awardId);


}

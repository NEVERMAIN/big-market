package com.openicu.domain.strategy.service.rule.chain;

/**
 * @description: 抽奖策略规则责任链接口
 * @author: 云奇迹
 * @date: 2024/6/21
 */
public interface ILogicChain extends ILogicChainArmory {

    /**
     * 责任链接口
     *
     * @param userId  用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    Integer logic(String userId,Long strategyId);

}

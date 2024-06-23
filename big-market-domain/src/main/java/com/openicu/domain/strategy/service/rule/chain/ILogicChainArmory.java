package com.openicu.domain.strategy.service.rule.chain;

/**
 * @description: 责任链装配
 * @author: 云奇迹
 * @date: 2024/6/21
 */
public interface ILogicChainArmory {

    /**
     * 获取下一个逻辑链单元。
     *
     * @return 返回下一个逻辑链单元，如果不存在，则返回null。
     */
    ILogicChain next();

    /**
     * 在当前逻辑链单元之后添加一个新的逻辑链单元。
     *
     * @param next 要添加的下一个逻辑链单元。
     * @return 返回添加后的逻辑链单元，以便进行链式操作。
     */
    ILogicChain appendNext(ILogicChain next);

}

package com.openicu.domain.activity.service.quota.rule;

/**
 * @description: 下单规则责任链抽象类
 * @author: 云奇迹
 * @date: 2024/7/9
 */
public abstract class AbstractActionChain implements IActionChain{

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}

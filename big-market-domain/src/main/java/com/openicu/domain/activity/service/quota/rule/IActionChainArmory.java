package com.openicu.domain.activity.service.quota.rule;

/**
 * @description: 责任链接口
 * @author: 云奇迹
 * @date: 2024/7/9
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);

}

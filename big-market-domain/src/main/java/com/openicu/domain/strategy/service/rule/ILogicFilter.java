package com.openicu.domain.strategy.service.rule;

import com.openicu.domain.strategy.model.entity.RuleActionEntity;
import com.openicu.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @description: 抽奖规则过滤接口
 * @author: 云奇迹
 * @date: 2024/6/19
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {


    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);


}

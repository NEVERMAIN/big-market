package com.openicu.domain.strategy.service.rule;

import com.openicu.domain.strategy.model.entity.RuleActionEntity;
import com.openicu.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @description: 抽奖规则过滤接口
 * @author: 云奇迹
 * @date: 2024/6/19
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {


    /**
     * 根据规则事项实体过滤规则动作实体。
     *
     * @param ruleMatterEntity 规则物料实体
     * @return 过滤后的规则动作实体，该实体与传入的规则事项实体相匹配。
     */
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);


}

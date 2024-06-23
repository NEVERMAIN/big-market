package com.openicu.domain.strategy.service.rule.filter.impl;

import com.openicu.domain.strategy.model.entity.RuleActionEntity;
import com.openicu.domain.strategy.model.entity.RuleMatterEntity;
import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.annotation.LogicStrategy;
import com.openicu.domain.strategy.service.rule.ILogicFilter;
import com.openicu.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 次数锁定
 * @author: 云奇迹
 * @date: 2024/6/20
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_LOCK)
public class RuleLockLogicFilter implements ILogicFilter<RuleActionEntity.RaffleCenterEntity> {

    @Resource
    private IStrategyRepository repository;

    // 用户抽奖次数,后续从数据库/redis 中读取
    private Long userRaffleCount = 0L;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleCenterEntity> filter(RuleMatterEntity ruleMatterEntity) {

        log.info("规则过滤 - 次数锁 userId:{} strategyId:{} ruleModel:{} ",ruleMatterEntity.getUserId(),ruleMatterEntity.getStrategyId(),ruleMatterEntity.getRuleModel());

        // 1. 查询规则配置值:当前奖品ID,抽奖中规则对应的校验值。如 1,2,6
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(),ruleMatterEntity.getRuleModel());
        long raffleCount = Long.parseLong(ruleValue);

        // 2. 用户抽奖次数大于规则限定值,规则放行
        if(userRaffleCount >= raffleCount){
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 3. 用户抽奖次数小于规则限定值,规则拦截
        return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                .build();
    }
}

package com.openicu.domain.strategy.service.rule.tree.impl;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyRepository repository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {

        log.info("规则树过滤-规则库存-开始 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);

        // 1.查询策略奖品的剩余库存
        Integer awardSurplusCount = repository.queryStrategyAwardSurplusCount(strategyId, awardId);

        // 2.判断剩余库存
        if(awardSurplusCount <= 0){
            log.info("规则树过滤-规则库存-接管 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .build();
        }

        // 3.剩余库存不为0,放行
        log.info("规则树过滤-规则库存-放行 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();

    }

}

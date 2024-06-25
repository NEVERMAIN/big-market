package com.openicu.domain.strategy.service.rule.tree.impl;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.armory.IStrategyDispatch;
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

    @Resource
    private IStrategyDispatch strategyDispatch;


    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId,String ruleValue) {

        log.info("规则过滤-库存扣减 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);

        // 1.扣减库存
        Boolean status = strategyDispatch.subtractionAwardStock(strategyId, awardId);

        // true: 库存扣减成功, TAKE_OVER 规则节点接管,返回奖品ID,奖品规则配置
        if(status){
            log.info("规则过滤-库存扣减-成功 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            // 写入延迟队列,延迟消费更新数据库记录【在 trigger 的 job:UpdateAwardStockJob 下消费队列，更新数据库记录】
            repository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                    .strategyId(strategyId)
                    .awardId(awardId)
                    .build());
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .strategyAwardData(DefaultTreeFactory.StrategyAwardData.builder()
                            .awardId(awardId)
                            .awardRuleValue(ruleValue)
                            .build())
                    .build();
        }

        // 如果库存不足，则直接返回放行
        log.warn("规则过滤-库存扣减-告警，库存不足。userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();

    }

}

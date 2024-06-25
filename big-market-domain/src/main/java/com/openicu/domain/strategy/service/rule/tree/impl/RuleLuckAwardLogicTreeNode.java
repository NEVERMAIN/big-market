package com.openicu.domain.strategy.service.rule.tree.impl;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.openicu.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 兜底幸运奖节点
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId,String ruleValue) {

        log.info("规则过滤-兜底奖品 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);

        String[] split = ruleValue.split(Constants.COLON);
        if(split.length == 0){
            log.error("规则过滤-兜底奖品，兜底奖品未配置告警 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置 " + ruleValue);
        }

        Integer luckAwardId = Integer.valueOf(split[0]);
        String awardRuleValue = split.length > 1 ? split[1] : "";

        // 3. 返回兜底奖品
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(DefaultTreeFactory.StrategyAwardData.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue)
                        .build())
                .build();

    }
}

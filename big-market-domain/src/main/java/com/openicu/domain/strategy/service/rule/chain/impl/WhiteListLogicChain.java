package com.openicu.domain.strategy.service.rule.chain.impl;

import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.openicu.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.openicu.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 白名单责任链
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("rule_whitelist")
public class WhiteListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("规则过滤-白名单开启 userId:{}, strategyId:{} , ruleModel:{}", userId, strategyId, ruleModel());

        // 1. 查询规则值配置
        String ruleValue = repository.queryStrategyRuleValue(strategyId, Integer.valueOf(userId), ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 过滤
        String[] userWhiteIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userWhiteId : userWhiteIds) {
            if (userId.equals(userWhiteId)) {
                log.info("规则过滤-白名单接管 userId:{} , strategyId:{} ruleModel:{} ", userId, strategyId, ruleModel());
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        .build();
            }
        }

        log.info("规则过滤-白名单放行 userId:{} strategyId:{} ruleModel:{} ", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }


    @Override
    protected String ruleModel() {
        return "rule_whitelist";
    }


}

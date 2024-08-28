package com.openicu.domain.strategy.service.rule.chain.impl;

import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.openicu.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.openicu.types.annotation.DCCValue;
import com.openicu.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 黑名单责任链
 * @author: 云奇迹
 * @date: 2024/6/21
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("rule_blacklist")
public class BackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    /** 黑名单控制开关 */
    @DCCValue("blackListSwitch:close")
    private String blackListSwitch;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {

        log.info("抽奖责任链-黑名单开始 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());

        // 判断开关是否关闭
       if(!"open".equals(blackListSwitch)){
           // 过滤其他责任链
           log.info("抽奖责任链-黑名单校验已关闭 userId:{} strategyId:{} ruleModel:{} blackListSwitch:{}", userId, strategyId, ruleModel(),blackListSwitch);
           return next().logic(userId, strategyId);
       }

        // 查询规则值配置
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 黑名单抽奖判断
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链-黑名单接管 userId: {} strategyId:{} ruleModel: {} awardId:{}", userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        //写入默认配置黑名单奖品值 0.01 ~ 1 积分，也可以配置到数据库表中
                        .awardRuleValue("0.01,1")
                        .build();
            }
        }

        // 过滤其他责任链
        log.info("抽奖责任链-黑名单放行 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);

    }

    @Override
    protected String ruleModel() {
        return "rule_blacklist";
    }
}

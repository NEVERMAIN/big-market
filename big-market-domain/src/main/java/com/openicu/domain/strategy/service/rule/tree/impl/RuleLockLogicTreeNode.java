package com.openicu.domain.strategy.service.rule.tree.impl;

import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.DefaultLogicFactory;
import com.openicu.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 次数锁定节点
 * @author: 云奇迹
 * @date: 2024/6/24
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyRepository repository;

    /**
     * 用户抽奖次数【后续从数据库/redis中读取】
     */
    private Long userRaffleCount = 0L;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId,String ruleValue) {

        log.info("规则树过滤开始--次数锁--开始 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId,awardId,ruleValue);

        long raffleCount = 0L;
        try{
            raffleCount = Long.parseLong(ruleValue);
        }catch (Exception e){
            throw new RuntimeException("规则树过滤-次数锁异常 ruleValue: " + ruleValue + " 配置不正确");
        }

        // 3. 用户抽奖次数大于规则限定值,规则放行
        if(userRaffleCount >= raffleCount){
            log.info("规则树过滤结束--次数锁--放行 userId:{} strategyId:{} awardId:{}", userId, strategyId,awardId);
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                    .build();
        }

        // 4. 用户抽奖次数小于规则限定值,规则拦截
        log.info("规则树过滤结束--次数锁--接管 userId:{} strategyId:{} awardId:{}", userId, strategyId,awardId);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();

    }

}

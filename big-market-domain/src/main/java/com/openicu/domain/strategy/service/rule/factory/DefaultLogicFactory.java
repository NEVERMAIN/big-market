package com.openicu.domain.strategy.service.rule.factory;

import com.openicu.domain.strategy.model.entity.RuleActionEntity;
import com.openicu.domain.strategy.service.annotation.LogicStrategy;
import com.openicu.domain.strategy.service.rule.ILogicFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 规则工厂
 * @author: 云奇迹
 * @date: 2024/6/19
 */
@Service
public class DefaultLogicFactory {

    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();

    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters){
        logicFilters.forEach(logic -> {
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(),LogicStrategy.class);
            if(null != strategy){
                logicFilterMap.put(strategy.logicMode().getCode(), logic);
            }
        });
    }

    public <T extends RuleActionEntity.RaffleBeforeEntity> Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }

    @AllArgsConstructor
    @Getter
    public enum LogicModel{

        RULE_WEIGHT("rule_weight","【抽奖前规则】根据抽奖权重返回可抽奖的范围 key"),
        RULE_BLACKLIST("rule_blacklist","【抽奖前规则】黑名单规则过滤,命中黑名单则直接返回"),
        ;

        private final String code;

        private final String info;

    }



}

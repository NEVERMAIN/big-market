package com.openicu.domain.strategy.service.rule.filter.factory;

import com.openicu.domain.strategy.model.entity.RuleActionEntity;
import com.openicu.domain.strategy.service.annotation.LogicStrategy;
import com.openicu.domain.strategy.service.rule.ILogicFilter;
import lombok.AllArgsConstructor;
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

    public <T extends RuleActionEntity.RaffleEntity> Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }


    @AllArgsConstructor
    @Getter
    public enum LogicModel{

        /**
         * 根据抽奖权重返回可抽奖的范围。
         * 使用该规则时，系统会根据物品的权重来决定哪些物品可以被抽中。
         */
        RULE_WEIGHT("rule_weight","【抽奖前规则】根据抽奖权重返回可抽奖的范围 key","before"),

        /**
         * 黑名单规则过滤。
         * 如果参与者命中了黑名单，根据该规则他们会直接被排除在抽奖之外。
         */
        RULE_BLACKLIST("rule_blacklist","【抽奖前规则】黑名单规则过滤,命中黑名单则直接返回","before"),

        /**
         * 白名单规则过滤
         * 如果参与者命中了白名单，根据该规则他们会直接中奖一个奖品
         */
        RULE_WHITELIST("rule_whitelist","【抽奖前规则】白名单规则过滤,命中白名单则直接中奖一个奖品","before"),
        /**
         * 默认规则
         */
        RULE_DEFAULT("rule_default","【抽奖前规则】默认抽奖","before"),

        /**
         * 奖品锁定
         */
        RULE_LOCK("rule_lock","【抽奖中规则】奖品锁定规则过滤,满足抽奖次数后才能解锁","center"),

        /**
         * 奖品锁定
         */
        RULE_LUCK_AWARD("rule_luck_award","【抽奖后规则】抽奖n次,对应奖品可解锁抽奖","after")

        ;

        private final String code;

        private final String info;

        private final String type;

        public static boolean isCenter(String code){
            return "center".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }

        public static boolean isAfter(String code){
            return "after".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }


    }



}

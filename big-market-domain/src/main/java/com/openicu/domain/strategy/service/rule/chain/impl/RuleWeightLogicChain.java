package com.openicu.domain.strategy.service.rule.chain.impl;

import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.armory.IStrategyDispatch;
import com.openicu.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.openicu.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.openicu.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * @description: 权重规则校验
 * @author: 云奇迹
 * @date: 2024/6/21
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Resource
    private IStrategyDispatch strategyDispatch;

    // 按需选择需要的计算策略，旧版是 AnalyticalNotEqual 算法，新增加 = 算法。使用时可以实例化 AnalyticalEqual 即可。这个操作也可以从数据库中配置处理。
    private final IAnalytical analytical = new AnalyticalNotEqual();


    /**
     * 根据用户ID查询用户抽奖消耗的积分值
     */
    public Long userScore = 0L;

    /**
     * 权重责任链过滤；
     * 1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式；判断哪个范围符合用户的特定抽奖范围
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {

        log.info("抽奖责任链-权重开始 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());

        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());

        // 1.解析权重规则值 4000:102,103,104,105 拆解为；4000 -> 4000:102,103,104,105 便于比对判断
        Map<Integer, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty()) {
            log.warn("抽奖责任链-权重告警【策略配置权重，但ruleValue未配置相应值】 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());
            return next().logic(userId, strategyId);
        }

        // 2. 用户分值
        Integer userScore = repository.queryActivityAccountTotalUseCount(userId, strategyId);

        // 3.获取权重对应的 key
        String analyticalValue = analytical.getAnalyticalValue(analyticalValueGroup, userScore);

        // 4.权重抽奖
        if (null != analyticalValue) {
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, analyticalValue);
            log.info("抽奖责任链-权重接管 userId:{} strategyId:{} ruleModel:{} awardId:{}", userId, strategyId, ruleModel(), awardId);
            return DefaultChainFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .logicModel(ruleModel())
                    .build();
        }

        // 5. 过滤其他责任链
        log.info("抽奖责任链-权重放行 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    /**
     * 4000:102,103,104,105
     * 5000:102,103,104,105,106,107
     * 6000:102,103,104,105,106,107,108,109
     */
    private Map<Integer, String> getAnalyticalValue(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Integer, String> ruleValueMap = new HashMap<>();
        for (String ruleValueKey : ruleValueGroups) {
            // 检查输入是否为空
            if (ruleValueKey == null || ruleValueKey.isEmpty()) {
                return ruleValueMap;
            }
            // 分割字符串以获取键和值
            String[] parts = ruleValueKey.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_value invalid input format" + ruleValueKey);
            }
            ruleValueMap.put(Integer.parseInt(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
    }

    interface IAnalytical {

        /**
         * 获取指定权重值
         * @param analyticalValueGroup
         * @param userScore
         * @return
         */
        String getAnalyticalValue(Map<Integer, String> analyticalValueGroup, Integer userScore);
    }

    static class AnalyticalEqual implements IAnalytical {

        @Override
        public String getAnalyticalValue(Map<Integer, String> analyticalValueGroup, Integer userScore) {
            return "";
        }
    }

    static class AnalyticalNotEqual implements IAnalytical {

        @Override
        public String getAnalyticalValue(Map<Integer, String> analyticalValueGroup, Integer userScore) {

            // 1. 转换 Keys 值,并默认排序
            List<Integer> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
            Collections.sort(analyticalSortedKeys);

            // 2.找出最小符合的值，也就是【4500 积分，能找到 4000:102,103,104,105】、【5000 积分，能找到 5000:102,103,104,105,106,107】
            Integer nextValue = analyticalSortedKeys.stream()
                    .sorted(Comparator.reverseOrder())
                    .filter(analyticalSortedKeyValue -> userScore >= analyticalSortedKeyValue)
                    .findFirst()
                    .orElse(null);

            // 3.返回权重范围的key值
            return analyticalValueGroup.get(nextValue);

        }
    }


}

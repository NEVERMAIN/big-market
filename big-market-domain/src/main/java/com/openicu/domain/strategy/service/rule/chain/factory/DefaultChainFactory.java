package com.openicu.domain.strategy.service.rule.chain.factory;

import com.openicu.domain.strategy.model.entity.StrategyEntity;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.rule.chain.ILogicChain;
import com.openicu.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 工厂
 * @author: 云奇迹
 * @date: 2024/6/22
 */
@Service
public class DefaultChainFactory {

    // 原型模式获取对象
    private final ApplicationContext applicationContext;
    // 仓储信息
    protected IStrategyRepository repository;
    // 存放策略链, 策略ID -> 责任链
    private final Map<Long,ILogicChain> strategyChainGroup;


    public DefaultChainFactory(ApplicationContext applicationContext, IStrategyRepository repository) {
        this.applicationContext = applicationContext;
        this.repository = repository;
        this.strategyChainGroup = new ConcurrentHashMap<>();
    }

    /**
     * 通过策略ID,构建责任链
     *
     * @param strategyId 策略ID
     * @return LogicChain
     */
    public ILogicChain openLogicChain(Long strategyId){

        ILogicChain cacheLogicChain  = strategyChainGroup.get(strategyId);
        if(null != cacheLogicChain) return cacheLogicChain;

        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        // 如果未配置策略规则,则只装填一个默认责任链
        if(null == ruleModels || 0 == ruleModels.length){
            ILogicChain ruleDefaultLogicChain = applicationContext.getBean(DefaultLogicFactory.LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class);
            // 写入缓存
            strategyChainGroup.put(strategyId,ruleDefaultLogicChain);
            return ruleDefaultLogicChain;
        }

        // 按照配置顺序填用户配置的责任链: rule_blacklist、rule_weight
        // 【注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存】
        ILogicChain logicChain = applicationContext.getBean(ruleModels[0], ILogicChain.class);
        ILogicChain current = logicChain;
        for(int i = 1 ; i < ruleModels.length ; i ++){
            ILogicChain nextChain = applicationContext.getBean(ruleModels[i], ILogicChain.class);
            current = current.appendNext(nextChain);
        }
        // 责任链的最后装填默认责任链
        current.appendNext(applicationContext.getBean(DefaultLogicFactory.LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class));
        // 写入缓存
        strategyChainGroup.put(strategyId,logicChain);

        return logicChain;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        /**
         * 抽奖奖品ID - 内部流转使用
         */
        private Integer awardId;
        /**
         *
         */
        private String logicModel;
    }



}

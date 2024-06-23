package com.openicu.domain.strategy.service;

import com.openicu.domain.strategy.model.entity.*;
import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.armory.IStrategyDispatch;
import com.openicu.domain.strategy.service.rule.chain.ILogicChain;
import com.openicu.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @description: 抽奖策略抽象类
 * @author: 云奇迹
 * @date: 2024/6/19
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务
    protected IStrategyRepository repository;

    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    protected DefaultChainFactory defaultChainFactory;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {

        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 获取抽奖责任链 - 前置规则的责任链处理
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);

        // 3.通过责任链,获取奖品ID
        Integer awardId = logicChain.logic(userId, strategyId);

        // 6. 查询奖品规则 [抽奖中(拿到奖品ID，过滤规则)，抽奖后(扣减完奖品库存后过滤,抽奖中拦截和无库存则走兜底)]
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);

        // 7. 抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(
                RaffleFactorEntity.builder().userId(userId).awardId(awardId).strategyId(strategyId).build(),
                strategyAwardRuleModelVO.raffleCenterRuleModelList());

        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())) {
            log.info("【临时日志】中奖中规则拦截,通过抽奖后规则 rule_luck_award 走兜底奖励");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截,通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build();
        }

        //  返回结果
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }

    /**
     * 执行抽奖中的规则检查。
     *
     * @param raffleFactorEntity 抽奖因子实体，包含了与抽奖相关的各种因素，如用户信息、抽奖次数等。
     * @param logic 可变参数，用于传递额外的逻辑检查条件或参数，增强了方法的灵活性。
     * @return 返回一个 RuleActionEntity 对象，该对象封装了检查结果和可能的抽奖中心实体。如果检查失败，可以返回null。
     */
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logic);


}

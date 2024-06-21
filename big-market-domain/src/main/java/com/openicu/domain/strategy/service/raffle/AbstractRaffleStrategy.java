package com.openicu.domain.strategy.service.raffle;

import com.openicu.domain.strategy.model.entity.*;
import com.openicu.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.openicu.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.IRaffleStrategy;
import com.openicu.domain.strategy.service.armory.IStrategyDispatch;
import com.openicu.domain.strategy.service.rule.factory.DefaultLogicFactory;
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

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {

        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.策略查询
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);

        // 3. 抽奖前 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffBeforeLogic(
                RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(),
                strategy.ruleModels());

        // 4. 检查抽奖前,规则过滤结果
        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())) {
            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())) {
                // 黑名单返回固定的奖品ID
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionEntity.getData().getAwardId())
                        .build();
            } else if (DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode().equals(ruleActionEntity.getRuleModel())) {
                // 权重根据返回的信息进行抽奖
                RuleActionEntity.RaffleBeforeEntity raffleBeforeEntity = ruleActionEntity.getData();
                String ruleWeightValueKey = raffleBeforeEntity.getRuleWeightValueKey();
                Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValueKey);
                return RaffleAwardEntity.builder()
                        .awardId(awardId)
                        .build();
            } else if (DefaultLogicFactory.LogicModel.RULE_WHITELIST.getCode().equals(ruleActionEntity.getRuleModel())) {
                // 白名单范围内直接中奖一个奖品
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionEntity.getData().getAwardId())
                        .build();
            }
        }

        // 5. 默认抽奖流程
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);

        // 6. 查询奖品规则 [抽奖中(拿到奖品ID，过滤规则)，抽奖后(扣减完奖品库存后过滤,抽奖中拦截和无库存则走兜底)]
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId,awardId);

        // 7. 抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(
                RaffleFactorEntity.builder().userId(userId).awardId(awardId).strategyId(strategyId).build(),
                strategyAwardRuleModelVO.raffleCenterRuleModelList());

        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())){
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
     * 执行抽奖前的规则检查。
     *
     * @param raffleFactorEntity 抽奖因子实体，包含了与抽奖相关的各种因素信息，用于逻辑检查。
     * @param logic 可变参数，额外的逻辑参数，用于支持更灵活的业务逻辑检查。
     * @return 返回一个 RuleActionEntity 对象，该对象封装了抽奖行为前的逻辑处理结果。
     */
    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logic);

    /**
     * 执行抽奖中的规则检查。
     *
     * @param raffleFactorEntity 抽奖因子实体，包含了与抽奖相关的各种因素，如用户信息、抽奖次数等。
     * @param logic 可变参数，用于传递额外的逻辑检查条件或参数，增强了方法的灵活性。
     * @return 返回一个 RuleActionEntity 对象，该对象封装了检查结果和可能的抽奖中心实体。如果检查失败，可以返回null。
     */
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logic);



}

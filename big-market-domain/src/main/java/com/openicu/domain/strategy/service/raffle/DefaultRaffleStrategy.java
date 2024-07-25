package com.openicu.domain.strategy.service.raffle;

import com.openicu.domain.strategy.model.entity.ActivityAwardEntity;
import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;
import com.openicu.domain.strategy.model.entity.StrategyRuleEntity;
import com.openicu.domain.strategy.model.valobj.RuleTreeVO;
import com.openicu.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.domain.strategy.service.AbstractRaffleStrategy;
import com.openicu.domain.strategy.service.IRaffleAward;
import com.openicu.domain.strategy.service.IRaffleStock;
import com.openicu.domain.strategy.service.armory.IStrategyDispatch;
import com.openicu.domain.strategy.service.rule.chain.ILogicChain;
import com.openicu.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.openicu.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.openicu.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 抽奖策略默认实现
 * @author: 云奇迹
 * @date: 2024/6/20
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy implements IRaffleStock, IRaffleAward {


    private final HttpServletResponse httpServletResponse;

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory, HttpServletResponse httpServletResponse) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    @Override
    public DefaultTreeFactory.StrategyAwardData raffleLogicTree(String userId, Long strategyId, Integer awardId) {

        // 1. 获取奖品配置的规则配置值
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        // 奖品规则配置为 null , 则直接返回
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardData.builder()
                    .awardId(awardId)
                    .build();
        }

        // 2. 感觉奖品的规则模型,获取规则树节点
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key,未在库表 rule_tree、rule_tree_node、rule_tree_node_line 配置对应的规则信息 ruleModels:" + strategyAwardRuleModelVO.getRuleModels());
        }

        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);

        // 3. 获取决策树引擎执行结果
        return treeEngine.process(userId, strategyId, awardId);
    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException {
        return repository.takeQueueValue();
    }

    @Override
    public void clearQueueValue() {
        repository.clearQueueValue();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.updateStrategyAwardStock(strategyId, awardId);
    }

    @Override
    public void clearStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.clearStrategyAwardStock(strategyId, awardId);
    }

    @Override
    public List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId) {
        return repository.queryStrategyAwardList(strategyId);
    }

    @Override
    public List<ActivityAwardEntity> queryActivityAwardList(Long activityId,String userId) {

        // 1.查询活动奖品列表
        Long strategyId = repository.queryStrategyIdByActivityId(activityId);
        List<StrategyAwardEntity> strategyAwardEntityList = repository.queryStrategyAwardList(strategyId);

        // 2.查询奖品规则配置
        List<ActivityAwardEntity> activityAwardEntityList = strategyAwardEntityList.stream().map(entity -> {

            ActivityAwardEntity activityAwardEntity = ActivityAwardEntity.builder()
                    .strategyId(entity.getStrategyId())
                    .awardId(entity.getAwardId())
                    .awardTitle(entity.getAwardTitle())
                    .awardSubtitle(entity.getAwardSubtitle())
                    .awardCount(entity.getAwardCount())
                    .awardCountSurplus(entity.getAwardCountSurplus())
                    .awardRate(entity.getAwardRate())
                    .sort(entity.getSort())
                    .build();

            StrategyRuleEntity strategyRule = repository.queryStrategyAwardRule(entity.getStrategyId(), entity.getAwardId());
            if (null != strategyRule) {
                activityAwardEntity.setRuleType(strategyRule.getRuleType());
                activityAwardEntity.setRuleModel(strategyRule.getRuleModel());
                activityAwardEntity.setRuleValue(strategyRule.getRuleValue());
            }

            return activityAwardEntity;

        }).collect(Collectors.toList());

        // 查询用户今天参与活动的次数
        Integer userRaffleCount = repository.queryTodayUserRaffleCount(userId, strategyId);

        activityAwardEntityList.forEach(entity->{
            entity.isAwardLock(userRaffleCount);
        });

        // 3.返回结果
        return activityAwardEntityList;


    }
}

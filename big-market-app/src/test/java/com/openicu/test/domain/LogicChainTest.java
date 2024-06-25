package com.openicu.test.domain;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.strategy.model.entity.RaffleAwardEntity;
import com.openicu.domain.strategy.model.entity.RaffleFactorEntity;
import com.openicu.domain.strategy.service.IRaffleStrategy;
import com.openicu.domain.strategy.service.armory.IStrategyArmory;
import com.openicu.domain.strategy.service.rule.chain.ILogicChain;
import com.openicu.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.openicu.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicChainTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;

    @Resource
    private DefaultChainFactory defaultChainFactory;

    @Resource
    private IRaffleStrategy raffleStrategy;


    @Test
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100003L));

    }

    @Test
    public void test_logicChain_rule_blackList() {

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100003L);
        DefaultChainFactory.StrategyAwardVO strategyAwardVO = logicChain.logic("user001", 100003L);
        log.info("测试结果: {}", strategyAwardVO);

    }

    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 5900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        DefaultChainFactory.StrategyAwardVO strategyAwardVO = logicChain.logic("咸鱼12138", 100001L);
        log.info("测试结果: {}", strategyAwardVO);
    }

    @Test
    public void test_LogicChain_rule_default() {

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        DefaultChainFactory.StrategyAwardVO strategyAwardVO = logicChain.logic("咸鱼12138", 100001L);
        log.info("测试结果: {}", strategyAwardVO);

    }

    @Test
    public void test_performRaffle(){

        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("咸鱼12138")
                .strategyId(100001L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数: {}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}", JSON.toJSONString(raffleAwardEntity));
    }

    @Test
    public void test_raffle_center_lock() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("咸鱼12138")
                .strategyId(100003L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数: {}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}", JSON.toJSONString(raffleAwardEntity));
    }


}

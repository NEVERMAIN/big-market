package com.openicu.test.domain;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.strategy.model.entity.RaffleAwardEntity;
import com.openicu.domain.strategy.model.entity.RaffleFactorEntity;
import com.openicu.domain.strategy.service.IRaffleStrategy;
import com.openicu.domain.strategy.service.rule.impl.RuleWeightLogicFilter;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
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
 * @date: 2024/6/20
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private RuleWeightLogicFilter ruleWeightLogicFilter;

    @Before
    public void setUp(){
        ReflectionTestUtils.setField(ruleWeightLogicFilter,"userScore", 4500L);
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
    public void test_performRaffle_blackList(){

        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("user003")
                .strategyId(100001L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数: {}",JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}",JSON.toJSONString(raffleAwardEntity));

    }



}

package com.openicu.test.domain;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.strategy.model.entity.RaffleAwardEntity;
import com.openicu.domain.strategy.model.entity.RaffleFactorEntity;
import com.openicu.domain.strategy.service.IRaffleStrategy;
import com.openicu.domain.strategy.service.armory.IStrategyArmory;
import com.openicu.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

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
    private IStrategyArmory strategyArmory;

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;

    @Before
    public void setUp(){
        // 策略装配 100001、100002、100003
        log.info("测试结果: {}",strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果: {}",strategyArmory.assembleLotteryStrategy(100006L));

        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain,"userScore",4900L);

    }

    @Test
    public void test_performRaffle(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("咸鱼12138")
                .strategyId(100006L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数: {}",JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}",JSON.toJSONString(raffleAwardEntity));

    }

    @Test
    public void test_performRaffle02() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("咸鱼12138")
                    .strategyId(100006L)
                    .build();
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
        }
        // 等待 UpdateAwardStockJob 消费队列
        new CountDownLatch(1).await();
    }


}


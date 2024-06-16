package com.openicu.test.domain;

import com.openicu.domain.strategy.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Test
    public void test_strategyArmory(){
        boolean success = strategyArmory.assembleLotteryStrategy(100001L);
        log.info("测试结果:{}",success);
    }


    @Test
    public void test_getAssembleRandomVal(){
        Integer randomAwardId = strategyArmory.getRandomAwardId(100001L);
        log.info("测试结果:{}",randomAwardId);
    }


}

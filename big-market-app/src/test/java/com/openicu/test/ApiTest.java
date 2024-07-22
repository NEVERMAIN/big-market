package com.openicu.test;

import com.openicu.domain.award.model.aggregate.UserAwardDistributionAggregate;
import com.openicu.domain.award.service.distribute.IAwardDistribution;
import com.openicu.domain.award.service.distribute.factory.AwardDistributionFactory;
import com.openicu.infrastructure.persistent.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IRedisService redisService;

    @Test
    public void test() {
        log.info("测试完成");
    }

    @Test
    public void test_getMap(){
    }


    @Resource
    private AwardDistributionFactory awardDistributionFactory;

    @Test
    public void test_awardDistributeFactory(){

        IAwardDistribution userCreditRandom = awardDistributionFactory.getAwardDistribution("user_credit_random");
        UserAwardDistributionAggregate userAwardDistributionAggregate = new UserAwardDistributionAggregate();
        userAwardDistributionAggregate.setUserId("咸鱼12138");
        userAwardDistributionAggregate.setOrderId("767103935834");
        Boolean success = userCreditRandom.doDistribution(userAwardDistributionAggregate);


    }



}

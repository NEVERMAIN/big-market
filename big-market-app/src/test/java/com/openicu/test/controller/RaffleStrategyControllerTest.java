package com.openicu.test.controller;

import com.alibaba.fastjson.JSON;
import com.openicu.trigger.api.IRaffleStrategyService;
import com.openicu.trigger.api.dto.*;
import com.openicu.trigger.api.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyControllerTest {

    @Resource
    private IRaffleStrategyService raffleStrategyService;

    @Test
    public void test_queryRaffleAwardList(){
        RaffleAwardListRequestDTO request = new RaffleAwardListRequestDTO();
        request.setUserId("咸鱼12138");
        request.setActivityId(100301L);
        Response<List<RaffleAwardListResponseDTO>> response = raffleStrategyService.queryRaffleAwardList(request);

        log.info("请求参数:{}", JSON.toJSONString(request));
        log.info("测试结果:{}",JSON.toJSONString(response));

    }

    @Test
    public void test_queryRaffleStrategyRuleWeight() throws InterruptedException {
        RaffleStrategyRuleWeightRequestDTO request = new RaffleStrategyRuleWeightRequestDTO();
        request.setUserId("星耀");
        request.setActivityId(100301L);
        Response<List<RaffleStrategyRuleWeightResponseDTO>> response =
                raffleStrategyService.queryRaffleStrategyRuleWeight(request);

        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));

        new CountDownLatch(1).await();

    }




}

package com.openicu.test.controller;

import com.alibaba.fastjson.JSON;
import com.openicu.trigger.api.IRaffleActivityService;
import com.openicu.trigger.api.dto.*;
import com.openicu.trigger.api.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
public class RaffleActivityControllerTest {

    @Resource
    private IRaffleActivityService raffleActivityService;

    @Test
    public void test_armory(){
        Response<Boolean> response = raffleActivityService.armory(100301L);
        log.info("测试结果:{}", JSON.toJSONString(response));

    }

    @Test
    public void test_draw() throws InterruptedException {

        ActivityDrawRequestDTO request = new ActivityDrawRequestDTO();
        request.setActivityId(100301L);
        request.setUserId("zookeeper");
        Response<ActivityDrawResponseDTO> response = raffleActivityService.draw(request);

        log.info("请求参数:{}",JSON.toJSONString(request));
        log.info("测试结果:{}",JSON.toJSONString(response));

        new CountDownLatch(1).await();
    }

    @Test
    public void test_calendarSignRebate() throws InterruptedException {

        Response<Boolean> response = raffleActivityService.calendarSignRebate("PS");
        log.info("测试结果：{}", JSON.toJSONString(response));

        new CountDownLatch(1).await();

    }

    @Test
    public void test_isCalendarSignRebate() {
        Response<Boolean> response = raffleActivityService.isCalendarSignRebate("zookeeper");
        log.info("测试结果: {}",JSON.toJSONString(response));


    }

    @Test
    public void test_queryUserActivityAccount() {

        UserActivityAccountRequestDTO request = new UserActivityAccountRequestDTO();
        request.setActivityId(100301L);
        request.setUserId("zookeeper");
        // 1. 查询数据
        Response<UserActivityAccountResponseDTO> response = raffleActivityService.queryUserActivityAccount(request);
        log.info("请求参数: {}",JSON.toJSONString(request));
        log.info("测试结果: {}",JSON.toJSONString(response));


    }

    @Test
    public void test_querySkuProductListByActivityId(){
        Long request = 100301L;
        Response<List<SkuProductResponseDTO>> response = raffleActivityService.querySkuProductListByActivityId(request);
        log.info("请求参数:{}",JSON.toJSONString(request));
        log.info("测试结果:{}",JSON.toJSONString(response));
    }

    @Test
    public void test_queryUserCreditAccount() {
        String request = "kiss";
        Response<BigDecimal> response = raffleActivityService.queryUserCreditAccount(request);
        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));
    }

    @Test
    public void test_creditPayExchangeSku() throws InterruptedException {
        SkuProductShopCartRequestDTO request = new SkuProductShopCartRequestDTO();
        request.setUserId("xiaofuge");
        request.setSku(9011L);
        Response<Boolean> response = raffleActivityService.creditPayExchangeSku(request);
        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));
        new CountDownLatch(1).await();
    }

}

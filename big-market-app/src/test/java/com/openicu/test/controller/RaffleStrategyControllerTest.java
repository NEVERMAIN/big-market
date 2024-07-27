package com.openicu.test.controller;

import com.alibaba.fastjson.JSON;
import com.openicu.trigger.api.IRaffleStrategyService;
import com.openicu.trigger.api.dto.RaffleAwardListRequestDTO;
import com.openicu.trigger.api.dto.RaffleAwardListResponseDTO;
import com.openicu.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

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



}

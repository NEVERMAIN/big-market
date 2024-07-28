package com.openicu.test.controller;

import com.alibaba.fastjson.JSON;
import com.openicu.trigger.api.IRaffleActivityService;
import com.openicu.trigger.api.dto.ActivityDrawRequestDTO;
import com.openicu.trigger.api.dto.ActivityDrawResponseDTO;
import com.openicu.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
    public void test_draw(){

        ActivityDrawRequestDTO request = new ActivityDrawRequestDTO();
        request.setActivityId(100301L);
        request.setUserId("咸鱼12138");
        Response<ActivityDrawResponseDTO> response = raffleActivityService.draw(request);

        log.info("请求参数:{}",JSON.toJSONString(request));
        log.info("测试结果:{}",JSON.toJSONString(response));
    }

}

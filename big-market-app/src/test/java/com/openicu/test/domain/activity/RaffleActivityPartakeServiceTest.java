package com.openicu.test.domain.activity;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.openicu.domain.activity.model.entity.UserRaffleOrderEntity;
import com.openicu.domain.activity.service.IRaffleActivityPartakeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/19
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityPartakeServiceTest {

    @Resource
    private IRaffleActivityPartakeService raffleActivityPartakeService;

    @Test
    public void test_createOrder(){
        // 1.请求参数
        PartakeRaffleActivityEntity partakeRaffleActivityEntity = new PartakeRaffleActivityEntity();
        partakeRaffleActivityEntity.setUserId("咸鱼12138");
        partakeRaffleActivityEntity.setActivityId(100301L);
        // 2.调用接口
        UserRaffleOrderEntity userRaffleOrder = raffleActivityPartakeService.createOrder(partakeRaffleActivityEntity);
        log.info("请求参数: {}", JSON.toJSONString(partakeRaffleActivityEntity));
        log.info("测试结果: {}",JSON.toJSONString(userRaffleOrder));
    }



}

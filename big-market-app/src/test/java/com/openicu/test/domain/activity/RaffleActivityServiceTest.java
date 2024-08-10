package com.openicu.test.domain.activity;

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
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/10
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityServiceTest {

    @Resource
    private IRaffleActivityService raffleActivityService;

    @Test
    public void test_calendarSignRebate() throws InterruptedException {

        Response<Boolean> response = raffleActivityService.calendarSignRebate("user001");
        log.info("测试结果：{}", JSON.toJSONString(response));

        // 让程序挺住方便测试，也可以去掉
        new CountDownLatch(1).await();

    }


    @Test
    public void test_blacklist_draw() throws InterruptedException {

        ActivityDrawRequestDTO request = new ActivityDrawRequestDTO();
        request.setActivityId(100301L);
        request.setUserId("user001");
        Response<ActivityDrawResponseDTO> response = raffleActivityService.draw(request);

        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));

        // 让程序挺住方便测试，也可以去掉
        new CountDownLatch(1).await();
    }


}

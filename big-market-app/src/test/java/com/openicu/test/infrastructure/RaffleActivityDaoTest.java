package com.openicu.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.openicu.infrastructure.persistent.dao.IRaffleActivityDao;
import com.openicu.infrastructure.persistent.po.RaffleActivity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityDaoTest {

    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Test
    public void test_test_queryRaffleActivityByActivityId(){
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(100301L);
        log.info("测试结果: {}", JSON.toJSONString(raffleActivity));
    }

}

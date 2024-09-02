package com.openicu.test.infrastructure;

import com.openicu.infrastructure.dao.IRaffleActivityCountDao;
import com.openicu.infrastructure.dao.po.RaffleActivityCount;
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
public class IRaffleActivityCountDaoTest {

    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    @Test
    public void test_queryRaffleActivityCountByActivityCountId(){
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(1L);
        log.info("测试结果: {}",raffleActivityCount);
    }

}

package com.openicu.test;

import com.alibaba.fastjson2.JSON;
import com.openicu.infrastructure.elasticsearch.IElasticSearchRaffleActivityOrderDao;
import com.openicu.infrastructure.elasticsearch.IElasticSearchUserRaffleOrderDao;
import com.openicu.infrastructure.elasticsearch.po.RaffleActivityOrder;
import com.openicu.infrastructure.elasticsearch.po.UserRaffleOrder;
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
 * @date: 2024/9/3
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchUserRaffleOrderDaoTest {

    @Resource
    private IElasticSearchUserRaffleOrderDao elasticSearchUserRaffleOrderDao;

    @Resource
    private IElasticSearchRaffleActivityOrderDao elasticSearchRaffleActivityOrderDao;


    @Test
    public void test_queryUserRaffleOrderList() {
        List<UserRaffleOrder> userRaffleOrders = elasticSearchUserRaffleOrderDao.queryUserRaffleOrderList();
        log.info("测试结果：{}", JSON.toJSONString(userRaffleOrders));
    }

    @Test
    public void test_queryRaffleActivityOrderList() {
        List<RaffleActivityOrder> raffleActivityOrders = elasticSearchRaffleActivityOrderDao.queryRaffleActivityOrderList();
        log.info("测试结果：{}", JSON.toJSONString(raffleActivityOrders));
    }

}

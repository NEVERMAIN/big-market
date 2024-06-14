package com.openicu.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import com.openicu.infrastructure.persistent.dao.IAwardDao;
import com.openicu.infrastructure.persistent.dao.IStrategyAwardDao;
import com.openicu.infrastructure.persistent.po.Award;
import com.openicu.infrastructure.persistent.po.StrategyAward;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: AwardDao 测试
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class StrategyAwardDaoTest {

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Test
    public void test_queryAwardList(){
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardList();
        log.info("测试结果:{}", JSON.toJSON(strategyAwards));
    }

}

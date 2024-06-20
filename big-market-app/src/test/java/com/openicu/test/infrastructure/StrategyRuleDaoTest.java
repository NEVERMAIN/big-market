package com.openicu.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import com.openicu.infrastructure.persistent.dao.IStrategyDao;
import com.openicu.infrastructure.persistent.dao.IStrategyRuleDao;
import com.openicu.infrastructure.persistent.po.Strategy;
import com.openicu.infrastructure.persistent.po.StrategyRule;
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
public class StrategyRuleDaoTest {

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Test
    public void test_queryAwardList(){
        List<StrategyRule> strategyRules = strategyRuleDao.queryStrategyRuleList();
        log.info("测试结果:{}", JSON.toJSON(strategyRules));
    }

    @Test
    public void test_queryStrategyRuleValue(){
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setRuleModel("rule_blacklist");
        strategyRule.setStrategyId(100001L);
        String ruleValue = strategyRuleDao.queryStrategyRuleValue(strategyRule);
        log.info("请求参数: {}",strategyRule);
        log.info("测试结果: {}",ruleValue);
    }

}

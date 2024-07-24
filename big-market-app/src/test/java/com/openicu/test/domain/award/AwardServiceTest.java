package com.openicu.test.domain.award;

import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import com.openicu.domain.award.model.valobj.AwardStateVO;
import com.openicu.domain.award.service.IAwardService;
import com.openicu.infrastructure.persistent.po.UserAwardRecord;
import jodd.util.RandomString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/20
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardServiceTest {

    @Resource
    private IAwardService awardService;

    @Test
    public void test_saveUserAwardRecord() throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            UserAwardRecordEntity userAwardRecordEntity = new UserAwardRecordEntity();
            userAwardRecordEntity.setUserId("咸鱼12138");
            userAwardRecordEntity.setActivityId(100301L);
            userAwardRecordEntity.setStrategyId(100006L);
            userAwardRecordEntity.setOrderId(RandomStringUtils.randomNumeric(12));
            userAwardRecordEntity.setAwardId(101);
            userAwardRecordEntity.setAwardTitle("OpenAI 增加使用次数");
            userAwardRecordEntity.setAwardTime(new Date());
            userAwardRecordEntity.setAwardState(AwardStateVO.create);
            awardService.saveUserAwardRecord(userAwardRecordEntity);
            Thread.sleep(500);
        }

        new CountDownLatch(1).await();

    }

    @Test
    public void test_saveUserAwardRecord_duplicate() throws InterruptedException {

        UserAwardRecordEntity userAwardRecordEntity = new UserAwardRecordEntity();
        userAwardRecordEntity.setUserId("咸鱼12138");
        userAwardRecordEntity.setActivityId(100301L);
        userAwardRecordEntity.setStrategyId(100006L);
        userAwardRecordEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        userAwardRecordEntity.setAwardId(101);
        userAwardRecordEntity.setAwardTitle("OpenAI 增加使用次数");
        userAwardRecordEntity.setAwardTime(new Date());
        userAwardRecordEntity.setAwardState(AwardStateVO.create);
        awardService.saveUserAwardRecord(userAwardRecordEntity);

        new CountDownLatch(1).await();

    }


}

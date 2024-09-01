package com.openicu.test.infrastructure;

import com.openicu.infrastructure.dao.IRaffleActivityAccountDao;
import com.openicu.infrastructure.dao.po.RaffleActivityAccount;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/30
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityAccountDaoTest {

    @Resource
    private IRaffleActivityAccountDao raffleActivityAccountDao;

    @Test
    public void test_insert() {
        RaffleActivityAccount raffleActivityAccount = new RaffleActivityAccount();
        raffleActivityAccount.setUserId("咸鱼12138");
        raffleActivityAccount.setActivityId(100301L);
        raffleActivityAccount.setTotalCount(60);
        raffleActivityAccount.setTotalCountSurplus(60);
        raffleActivityAccount.setDayCount(2);
        raffleActivityAccount.setDayCountSurplus(2);
        raffleActivityAccount.setMonthCount(60);
        raffleActivityAccount.setMonthCountSurplus(60);

        raffleActivityAccountDao.insert(raffleActivityAccount);
    }

}

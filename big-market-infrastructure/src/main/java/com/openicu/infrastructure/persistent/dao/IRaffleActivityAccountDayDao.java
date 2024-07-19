package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.openicu.infrastructure.persistent.po.RaffleActivityAccountDay;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 抽奖活动账户表-日次数DAO
 * @author: 云奇迹
 * @date: 2024/7/16
 */
@Mapper
public interface IRaffleActivityAccountDayDao {

    /**
     * 查询用户账户日次数余额
     * @param raffleActivityAccountDay 请求
     * @return 用户账户日次数余额
     */
    @DBRouter(key = "userId")
    RaffleActivityAccountDay queryActivityAccountDayByUserId(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 减少用户账户日次数余额
     * @param raffleActivityAccountDay
     */
    Integer updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 创建活动账户日额度
     * @param raffleActivityAccountDay
     */
    void insertActivityAccountDay(RaffleActivityAccountDay raffleActivityAccountDay);
}

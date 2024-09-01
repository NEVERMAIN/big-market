package com.openicu.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.openicu.infrastructure.dao.po.RaffleActivityAccountDay;
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

    /**
     * 查询活动账户 - 日，参与次数
      * @param raffleActivityAccountDay
     * @return
     */
    @DBRouter(key = "userId")
    Integer queryRaffleActivityAccountDayPartakeCount(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 增加活动账户日额度
     * @param raffleActivityAccountDay
     */
    void addAccountQuota(RaffleActivityAccountDay raffleActivityAccountDay);

}

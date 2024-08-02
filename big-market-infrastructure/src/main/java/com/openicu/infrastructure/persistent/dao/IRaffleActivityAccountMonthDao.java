package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.openicu.infrastructure.persistent.po.RaffleActivityAccountMonth;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 抽奖活动账户表-月次数DAO
 * @author: 云奇迹
 * @date: 2024/7/16
 */
@Mapper
public interface IRaffleActivityAccountMonthDao {


    /**
     * 根据用户ID查询抽奖活动账户表-月次数
     * @param req 请求
     * @return 抽奖活动账户表-月次数
     */
    @DBRouter(key = "userId")
    RaffleActivityAccountMonth queryActivityAccountMonthByUserId(RaffleActivityAccountMonth req);

    /**
     * 创建抽奖活动账户月次数记录
     * @param raffleActivityAccountMonth
     */
    void insertActivityAccountMonth(RaffleActivityAccountMonth raffleActivityAccountMonth);

    /**
     * 减去抽奖活动账户月次数记录
     * @param raffleActivityAccountMonth
     * @return
     */
    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);

    void addAccountQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);
}

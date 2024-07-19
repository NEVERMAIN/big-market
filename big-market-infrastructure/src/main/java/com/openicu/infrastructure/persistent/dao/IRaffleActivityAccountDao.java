package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.openicu.infrastructure.persistent.po.RaffleActivityAccount;
import com.openicu.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 抽奖活动账户DAO
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Mapper
public interface IRaffleActivityAccountDao {

    /**
     * 保存用户活动账户的数据
     * @param raffleActivityAccount 活动账户实体化对象
     */
    void insert(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更新用户活动账户的库存
     * @param raffleActivityAccount 活动账户实体化对象
     * @return 更新记录数
     */
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 根据用户ID和活动ID查询用户活动总账户
     * @param raffleActivityAccount 活动账户实体化对象
     * @return 用户活动总账户
     */
    @DBRouter(key = "userId")
    RaffleActivityAccount queryActivityAccountByUserId(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更新用户活动总账户,扣减余额
     * @param raffleActivityAccount
     * @return
     */
    int updateActivityAccountSubtractionQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更新用户活动总账户中的月次数的镜像余额
     * @param raffleActivityAccount
     */
    void updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更新用户活动总账户中的日次数的镜像余额
     * @param raffleActivityAccount
     */
    void updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount raffleActivityAccount);
}

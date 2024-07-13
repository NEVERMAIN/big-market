package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.openicu.infrastructure.persistent.po.RaffleActivityAccount;
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

}

package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.UserAwardRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 用户中奖记录表DAO
 * @author: 云奇迹
 * @date: 2024/7/16
 */
@Mapper
public interface IUserAwardRecordDao {

    /**
     * 创建用户抽奖奖品记录
     * @param userAwardRecord
     */
    void insert(UserAwardRecord userAwardRecord);
}

package com.openicu.domain.award.reposiotry;

import com.openicu.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @description: 奖品仓储服务接口
 * @author: 云奇迹
 * @date: 2024/7/19
 */
public interface IAwardRepository {

    /**
     * 保存用户抽奖奖品记录
     * @param userAwardRecordAggregate
     */
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

}

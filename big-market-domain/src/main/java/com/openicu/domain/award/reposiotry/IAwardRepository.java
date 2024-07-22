package com.openicu.domain.award.reposiotry;

import com.openicu.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.openicu.domain.award.model.entity.AwardEntity;

/**
 * @description: 奖品仓储服务接口
 * @author: 云奇迹
 * @date: 2024/7/19
 */
public interface IAwardRepository {

    /**
     * 保存用户抽奖奖品记录
     * @param userAwardRecordAggregate 用户抽奖奖品聚合对象
     */
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

    /**
     * 更新用户抽奖奖品记录为发奖成功
     * @param
     */
    void updateUserAwardRecordCompleted(String orderId,String userId);

    /**
     * 查询奖品详细信息
     * @param awardId
     * @return
     */
    AwardEntity queryAwardByAwardId(Integer awardId);
}

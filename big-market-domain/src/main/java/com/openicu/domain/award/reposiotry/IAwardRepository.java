package com.openicu.domain.award.reposiotry;

import com.openicu.domain.award.model.aggregate.GiveOutPrizesAggregate;
import com.openicu.domain.award.model.aggregate.UserAwardRecordAggregate;

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
     * 查询奖品配置
     * @param awardId 奖品id
     * @return 奖品配置
     */
    String queryAwardConfig(Integer awardId);

    /**
     * 保存奖品发放聚合
     * @param giveOutPrizesAggregate 奖品发放聚合
     */
    void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate);

    /**
     * 查询奖品key
     * @param awardId
     * @return
     */
    String queryAwardKey(Integer awardId);
}

package com.openicu.domain.award.service;

import com.openicu.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @description: 奖品服务
 * @author: 云奇迹
 * @date: 2024/7/19
 */
public interface IAwardService {

    /**
     * 保存用户抽奖奖品记录
     * @param userAwardRecordEntity 奖品记录实体对象
     */
    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);


}

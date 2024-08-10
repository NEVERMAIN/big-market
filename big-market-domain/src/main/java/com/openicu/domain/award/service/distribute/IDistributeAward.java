package com.openicu.domain.award.service.distribute;

import com.openicu.domain.award.model.entity.DistributeAwardEntity;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/9
 */
public interface IDistributeAward {

    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);

}

package com.openicu.domain.award.service.distribute.impl;

import com.openicu.domain.award.model.entity.DistributeAwardEntity;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.domain.award.service.distribute.IDistributeAward;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/10
 */
@Component("openai_use_count")
public class OpenaiUseCountAward implements IDistributeAward {

    @Resource
    private IAwardRepository awardRepository;

    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) {

    }
}

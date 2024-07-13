package com.openicu.domain.activity.service.rule.impl;

import com.openicu.domain.activity.model.entity.ActivityCountEntity;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.ActivitySkuEntity;
import com.openicu.domain.activity.model.valobj.ActivityStateVO;
import com.openicu.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: 活动规则过滤【日期、状态】
 * @author: 云奇迹
 * @date: 2024/7/9
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {

        log.info("活动责任链-基础信息 【有效期、状态】校验开始...");
        Date beginDateTime = activityEntity.getBeginDateTime();
        Date endDateTime = activityEntity.getEndDateTime();
        String state = activityEntity.getState();

        if(beginDateTime.after(new Date())){
            return false;
        }

        if(endDateTime.before(new Date())){
            return false;
        }

        if(!state.equals(ActivityStateVO.OPEN.getCode())){
            return false;
        }

        return next().action(activitySkuEntity,activityEntity,activityCountEntity);
    }
}

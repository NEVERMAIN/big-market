package com.openicu.domain.activity.model.entity;

import com.openicu.domain.activity.model.valobj.ActivityStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {

    /** 活动ID */
    private Long activityId;
    /** 活动名称 */
    private String activityName;
    /** 活动描述 */
    private String activityDesc;
    /** 开始时间 */
    private Date beginDateTime;
    /** 结束时间 */
    private Date endDateTime;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 活动状态 */
    private ActivityStateVO state;


}

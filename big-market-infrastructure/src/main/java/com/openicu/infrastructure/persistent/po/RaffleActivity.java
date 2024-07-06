package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 抽奖活动
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaffleActivity {

    /** 自增ID */
    private Long id;
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
    private String state;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}

package com.openicu.infrastructure.persistent.po;

import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 抽奖活动账户表
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaffleActivityAccount {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;
    /** 总次数 */
    private Integer totalCount;
    /** 总次数-剩余 */
    private Integer totalCountSurplus;
    /** 日次数 */
    private Integer dayCount;
    /** 日次数-剩余 */
    private Integer dayCountSurplus;
    /** 月次数 */
    private Integer monthCount;
    /** 月次数-剩余 */
    private Integer monthCountSurplus;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}

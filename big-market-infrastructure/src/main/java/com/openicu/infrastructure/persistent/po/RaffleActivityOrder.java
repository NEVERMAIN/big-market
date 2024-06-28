package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 抽奖活动单
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaffleActivityOrder {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;
    /** 活动名称 */
    private String activityName;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 订单ID */
    private String orderId;
    /** 下单时间 */
    private Date orderTime;
    /** 订单状态（not_used、used、expire） */
    private String state;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}

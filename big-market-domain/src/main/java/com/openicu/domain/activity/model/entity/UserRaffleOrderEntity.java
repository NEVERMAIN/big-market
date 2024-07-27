package com.openicu.domain.activity.model.entity;

import com.openicu.domain.activity.model.valobj.UserRaffleOrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRaffleOrderEntity {

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
    /** 订单创建时间  */
    private Date orderTime;
    /** 订单状态: create-创建 used-已使用 cancel-已作废 */
    private UserRaffleOrderState orderState;
    /** 活动结束时间 */
    private Date endDateTime;


}

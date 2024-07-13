package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 活动下单记录表
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleActivityOrder {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String userId;
    /** 商品SKU */
    private Long sku;
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
    /** 总次数 */
    private Integer totalCount;
    /** 日次数 */
    private Integer dayCount;
    /** 月次数 */
    private Integer monthCount;
    /** 订单状态（complete） */
    private String state;
    /** 业务防重ID */
    private String outBusinessNo;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}

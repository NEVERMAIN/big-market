package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 抽奖活动SKU持久化对象
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaffleActivitySku {

    /** 自增ID */
    private Long id;
    /** 商品sku - 把每一个组合当做一个商品 */
    private Long sku;
    /** 活动ID */
    private Long activityId;
    /** 活动个人参与次数ID */
    private Long activityCountId;
    /** 商品库存 */
    private Integer stockCount;
    /** 剩余库存 */
    private Integer stockCountSurplus;
    /** 商品金额【积分】 */
    private BigDecimal productAmount;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}

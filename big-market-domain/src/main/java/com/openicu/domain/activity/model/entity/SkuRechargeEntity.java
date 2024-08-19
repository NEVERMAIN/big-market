package com.openicu.domain.activity.model.entity;

import com.openicu.domain.activity.model.valobj.OrderTradeTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 活动购物车实体对象
 * @author: 云奇迹
 * @date: 2024/7/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuRechargeEntity {

    /** 用户ID */
    private String userId;
    /** 商品SKU activity + activity_count */
    private Long sku;
    /** 外部透传 - 幂等性业务单号 */
    private String outBusinessNo;
    /** 交易类型 */
    private OrderTradeTypeVO orderTradeType;

}

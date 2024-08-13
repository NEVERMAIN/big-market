package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreditOrder {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String userId;
    /** 订单ID */
    private String orderId;
    /** 交易名称 */
    private String tradeName;
    /** 交易类型 */
    private String tradeType;
    /** 交易金额 */
    private BigDecimal tradeAmount;
    /** 业务仿重ID - 外部透传。返利、行为等唯一标识 */
    private String outBusinessNo;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;


}

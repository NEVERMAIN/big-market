package com.openicu.domain.rebate.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorRebateOrderEntity {

    /** 用户ID */
    private String userId;
    /** 订单ID */
    private String orderId;
    /** 行为类型（sign 签到、openai_pay 支付） */
    private String behaviorType;
    /** 返利描述 */
    private String rebateDesc;
    /** 返利类型（sku 活动库存充值商品、integral 用户活动积分） */
    private String rebateType;
    /** 返利配置【sku值，积分值】 */
    private String rebateConfig;
    /** 业务ID - 外部唯一 */
    private String outBusinessNo;
    /** 业务ID - 拼接的唯一值 */
    private String bizId;


}

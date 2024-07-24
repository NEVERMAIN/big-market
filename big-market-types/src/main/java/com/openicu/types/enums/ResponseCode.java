package com.openicu.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 云奇迹
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "调用成功"),
    UN_ERROR("0001", "调用失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    INDEX_DUP("0003", "唯一索引冲突"),

    STRATEGY_RULE_WEIGHT_IS_NULL("ERR_BIZ_001", "业务异常，策略规则中 rule_weight 权重规则已适用但未配置"),
    UN_ASSEMBLED_STRATEGY_ARMORY("ERR_BIZ_002", "抽奖策略配置未装配，请通过IStrategyArmory完成装配"),

    ACTIVITY_STATE_ERROR("ERR_BIZ_003", "活动未开启（非open状态）"),
    ACTIVITY_DATE_ERROR("ERR_BIZ_004", "非活动日期范围"),
    ACTIVITY_SKU_STOCK_ERROR("ERR_BIZ_005", "活动库存不足"),

    ACCOUNT_QUOTA_ERROR("ERR_BIZ_006","用户账户总次数-余额不够"),
    ACCOUNT_MONTH_QUOTA_ERROR("ERR_BIZ_007","用户账户月次数-余额不够"),
    ACCOUNT_DAY_QUOTA_ERROR("ERR_BIZ_008","用户账户日次数-余额不够"),
    ACTIVITY_ORDER_ERROR("ERR_BIZ_009","用户抽奖单已使用过，不可重复抽奖"),



    ;

    private String code;
    private String info;

}

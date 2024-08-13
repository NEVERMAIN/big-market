package com.openicu.domain.credit.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Getter
@AllArgsConstructor
public enum TradeNameVO {

    REBATE("行为返利"),
    CONVERT_SKU("兑换抽奖"),


    ;

    private final String name;
}

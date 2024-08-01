package com.openicu.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
@Getter
@AllArgsConstructor
public enum BehaviorTypeVO {

    SIGN("sign","签到(日历)"),
    OPENAI_PAY("openai_pay","openai 外部支付成功"),

    ;

    private final String code;
    private final String info;
}

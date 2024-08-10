package com.openicu.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/9
 */
@AllArgsConstructor
@Getter
public enum AccountStatusVO {

    /** 账户状态【open - 可用，close - 冻结】 */
    open("open", "可用"),
    close("close", "冻结"),

    ;

    private String code;
    private String info;
}

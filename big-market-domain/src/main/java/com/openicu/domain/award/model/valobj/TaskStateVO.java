package com.openicu.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/19
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {

    create("create", "创建"),
    complete("complete", "发送完成"),
    fail("fail", "发送失败"),


    ;

    private final String code;
    private final String info;

}

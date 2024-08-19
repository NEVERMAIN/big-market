package com.openicu.domain.credit.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/16
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {

    /** 任务状态；create-创建、completed-完成、fail-失败 */
    create("create", "创建"),
    completed("completed", "完成"),
    fail("fail", "失败"),

    ;

    private final String code;
    private final String info;
}


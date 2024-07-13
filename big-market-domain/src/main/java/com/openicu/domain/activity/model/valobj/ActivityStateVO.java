package com.openicu.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 活动状态值对象
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Getter
@AllArgsConstructor
public enum ActivityStateVO {

    CREATE("create","创建"),
    OPEN("open","开启"),
    CLOSE("close","关闭"),

    ;

    private final String code;
    private final String info;

}

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
public enum AwardStateVO {

    /** 奖品状态；create-创建、completed-发奖完成 */
    create("create", "创建"),
    complete("complete", "发奖完成"),
    fail("fail", "发奖失败"),


    ;

    private String code;
    private String info;
}

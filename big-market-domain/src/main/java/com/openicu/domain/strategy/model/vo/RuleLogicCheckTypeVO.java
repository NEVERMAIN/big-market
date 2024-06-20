package com.openicu.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 规则过滤校验类型值对象
 * @author: 云奇迹
 * @date: 2024/6/19
 */
@Getter
@AllArgsConstructor
public enum RuleLogicCheckTypeVO {

    ALLOW("0000","放行;执行后续的流程,不受规则引擎的影响"),
    TAKE_OVER("0001","接管,后续的流程,受规则引擎执行结果影响"),
    ;

    private final String code;

    private final String info;

}

package com.openicu.types.annotation;

import java.lang.annotation.*;

/**
 * @description: 注解-动态配置中心
 * @author: 云奇迹
 * @date: 2024/8/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DCCValue {

    String value() default "";


}

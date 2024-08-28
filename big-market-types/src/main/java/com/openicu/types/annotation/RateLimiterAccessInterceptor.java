package com.openicu.types.annotation;

import java.lang.annotation.*;

/**
 * @description: 注解-限流
 * @author: 云奇迹
 * @date: 2024/8/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RateLimiterAccessInterceptor {

    /** 用哪个字段作为拦截标志,未配置则默认全部 */
    String key() default "all";

    /** 限频频次(每秒请求次数) */
    double permitPerSecond();

    /** 黑名单拦截(多少次限制后加入黑名单) 0-不限制 */
    double blacklistCount() default 0;

    /** 拦截后执行的方法 */
    String fallbackMethod();

}

package com.openicu.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import com.openicu.types.annotation.DCCValue;
import com.openicu.types.annotation.RateLimiterAccessInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @description: 限流
 * @author: 云奇迹
 * @date: 2024/8/28
 */
@Slf4j
@Component
@Aspect
public class RateLimiterAOP {

    @DCCValue("rateLimiterSwitch:close")
    private String rateLimiterSwitch;

    /** 个人限频访问次数 */
    private final Cache<String, RateLimiter> loginRecord  = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    /** 个人限频黑名单 */
    private final Cache<String,Long> blackList = CacheBuilder.newBuilder()
            .expireAfterWrite(24,TimeUnit.HOURS)
            .build();

    @Pointcut("@annotation(com.openicu.types.annotation.RateLimiterAccessInterceptor)")
    public void aopPoint(){}

    @Around("aopPoint() && @annotation(RateLimiterAccessInterceptor)")
    public Object doRouter(ProceedingJoinPoint jp, RateLimiterAccessInterceptor RateLimiterAccessInterceptor) throws Throwable {
        // 0. 限流开关【open-开启 close-关闭】关闭后,不会走限流
        if(StringUtils.isBlank(rateLimiterSwitch) || "close".equals(rateLimiterSwitch)){
            return jp.proceed();
        }

        String key = RateLimiterAccessInterceptor.key();
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("annotation RateLimiter uId  is null!");
        }

        // 获取拦截字段
        String keyAttr = getAttrValue(key, jp.getArgs());
        log.info("aop attr{}",keyAttr);

        // 黑名单拦截
        if(!"all".equals(keyAttr) && RateLimiterAccessInterceptor.blacklistCount() != 0 && null != blackList.getIfPresent(keyAttr) && blackList.getIfPresent(keyAttr) > RateLimiterAccessInterceptor.blacklistCount()){
            log.info("限流-黑名单拦截(24h): {}",keyAttr);
            return fallbackMethodResult(jp, RateLimiterAccessInterceptor.fallbackMethod());
        }

        // 获取限流 -> Guava 缓存1分钟
        RateLimiter rateLimiter  = loginRecord.getIfPresent(keyAttr);
        if(null == rateLimiter){
            rateLimiter = RateLimiter.create(RateLimiterAccessInterceptor.permitPerSecond());
            loginRecord.put(keyAttr,rateLimiter);
        }

        // 限流拦截
        if(!rateLimiter.tryAcquire()){
            if(RateLimiterAccessInterceptor.blacklistCount() != 0){
                if(null == blackList.getIfPresent(keyAttr)){
                    blackList.put(keyAttr,1L);
                }else{
                    blackList.put(keyAttr, blackList.getIfPresent(keyAttr) + 1L);
                }
            }

            log.info("限流-超频次拦截：{}", keyAttr);
            return fallbackMethodResult(jp, RateLimiterAccessInterceptor.fallbackMethod());
        }

        // 返回结果
        return jp.proceed();

    }

    /**
     * 调用用户配置的回调方法，当拦截后，返回回调结果。
     * @param jp
     * @param fallbackMethod
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object fallbackMethodResult(JoinPoint jp,String fallbackMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = jp.getTarget().getClass().getMethod(fallbackMethod, methodSignature.getParameterTypes());
        return method.invoke(jp.getThis(),jp.getArgs());

    }

    /**
     * 实际根据自身业务调整，主要是为了获取通过某个值做拦截
     * @param attr
     * @param args
     * @return
     */
    public String getAttrValue(String attr,Object[] args){
        if(args[0] instanceof String){
            return args[0].toString();
        }
        String fieldValue = null;
        for (Object arg : args) {
            try {

                if(StringUtils.isNotBlank(fieldValue)){
                    break;
                }
                fieldValue = String.valueOf(this.getValueByName(arg, attr));
            }catch (Exception e){
                log.error("获取路由属性值失败 attr:{}",attr,e);
            }
        }
        return fieldValue;
    }

    /**
     * 获取对象的特定属性值
     *
     * @param item 对象
     * @param name 属性名
     * @return 属性值
     * @author tang
     */
    private Object getValueByName(Object item, String name) {
        try {
            Field field = getFieldByName(item, name);
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            Object o = field.get(item);
            field.setAccessible(false);
            return o;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 根据名称获取方法，该方法同时兼顾继承类获取父类的属性
     *
     * @param item 对象
     * @param name 属性名
     * @return 该属性对应方法
     * @author tang
     */
    private Field getFieldByName(Object item, String name) {
        try {
            Field field;
            try {
                field = item.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                field = item.getClass().getSuperclass().getDeclaredField(name);
            }
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

}

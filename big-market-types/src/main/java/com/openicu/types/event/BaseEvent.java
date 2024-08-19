package com.openicu.types.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 基础消息类
 * @author: 云奇迹
 * @date: 2024/7/14
 */
public abstract class BaseEvent<T> {

    /**
     * 构建消息
     * @param data
     * @return
     */
    public abstract EventMessage<T> buildEventMessage(T data);

    /**
     * 消息主题
     * @return
     */
    public abstract String topic();


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventMessage<T>{
        private String id;
        private Date timestamp;
        private T data;
    }



}

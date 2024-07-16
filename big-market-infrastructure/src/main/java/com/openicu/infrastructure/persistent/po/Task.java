package com.openicu.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 任务表
 * @author: 云奇迹
 * @date: 2024/7/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /** 自增ID */
    private Long id;
    /** 消息主题 */
    private String topic;
    /** 消息主体 */
    private String message;
    /** 任务状态: create-创建、completed-完成、fail-失败 */
    private String state;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}

package com.openicu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: Zookeeper 配置属性
 * @author: 云奇迹
 * @date: 2024/8/25
 */
@Data
@ConfigurationProperties(prefix = "zookeeper.sdk.config", ignoreInvalidFields = true)
public class ZookeeperClientConfigProperties {

    /** 状态 true 开启 ; close 关闭 */
    private boolean enable;

    /** 数据库连接字符串，用于建立数据库连接 */
    private String connectString;

    /** 基础睡眠时间，单位为毫秒，在重试前等待的时间 */
    private int baseSleepTimeMs;

    /** 最大重试次数，当操作失败时允许的最大重试次数 */
    private int maxRetries;

    /** 会话超时时间，单位为毫秒，用于设置数据库会话的超时时间 */
    private int sessionTimeoutMs;

    /** 连接超时时间，单位为毫秒，用于设置建立数据库连接的超时时间 */
    private int connectionTimeoutMs;

}

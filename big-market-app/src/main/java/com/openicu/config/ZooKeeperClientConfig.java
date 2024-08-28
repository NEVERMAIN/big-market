package com.openicu.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Zookeeper 客户端配置类
 * @author: 云奇迹
 * @date: 2024/8/25
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ZookeeperClientConfigProperties.class)
public class ZooKeeperClientConfig {

    /** 多参数构建 Zookeeper 客户端连接 */
    @Bean(name = "zookeeperClient")
    public CuratorFramework createWithOptions(ZookeeperClientConfigProperties properties) {

        // 1. 创建一个指数退避重试策略对象，用于在连接丢失或操作失败时重试
        // baseSleepTimeMs 为每次重试之间的初始睡眠时间，maxRetries 为最大重试次数
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries());

        // 2.构建 Curator 客户端，用于与 ZooKeeper 集群进行交互
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(properties.getConnectString())
                .retryPolicy(backoffRetry)
                .sessionTimeoutMs(properties.getSessionTimeoutMs())
                .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                .build();

        // 3.尝试启动客户端，并处理可能的异常
        try {
            client.start();
            // 记录成功启动的信息
            log.info("Zookeeper client started successfully.");
        } catch (Exception e) {
            // 记录启动失败的错误信息
            log.error("Failed to start Zookeeper client: {}", e.getMessage(), e);
            // 在这里可以选择抛出自定义异常或者进行其他处理
            throw new RuntimeException("Failed to start Zookeeper client", e);
        } finally {
            // 确保在发生异常时关闭客户端
            if (client != null && !client.isStarted()) {
                client.close();
            }
        }

        return client;

    }

}

package com.openicu.config;

import com.openicu.types.annotation.DCCValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 基于 Zookeeper 的配置中心实现原理
 * @author: 云奇迹
 * @date: 2024/8/25
 */
@Slf4j
@Configuration
public class DCCValueBeanFactory implements BeanPostProcessor {

    private static final String BASE_CONFIG_PATH = "/big-market-dcc";

    private static final String BASE_CONFIG_PATH_CONFIG = BASE_CONFIG_PATH + "/config";

    /** Zookeeper 客户端 */
    private final CuratorFramework client;

    private final Map<String, Object> dccObjGroup = new HashMap<>();

    public DCCValueBeanFactory(CuratorFramework client) throws Exception {

        this.client = client;

        // 节点判断
        if (null == client.checkExists().forPath(BASE_CONFIG_PATH_CONFIG)) {
            client.create().creatingParentsIfNeeded().forPath(BASE_CONFIG_PATH_CONFIG);
            log.info("DCC 节点监听 base node {} not absent create new done!", BASE_CONFIG_PATH_CONFIG);
        }

        // 构建 Zookeeper 缓存
        CuratorCache curatorCache = CuratorCache.build(client, BASE_CONFIG_PATH_CONFIG);
        curatorCache.start();

        // 添加 ZooKeeper 缓存监听器，监听节点变化
        curatorCache.listenable().addListener((type, oldData, data) -> {
            switch (type) {
                case NODE_CHANGED:
                    handleNodeChanged(data);
                    break;
                default:
                    break;
            }
        });

    }

    /**
     * 处理 Zookeeper 节点值发生变化的方法
     * @param data
     */
    private void handleNodeChanged(ChildData data) {
        String dccValuePath = data.getPath();
        Object objBean = dccObjGroup.get(dccValuePath);
        if (null == objBean) return;

        try {
            String fieldName = dccValuePath.substring(dccValuePath.lastIndexOf("/") + 1);
            Field field = objBean.getClass().getDeclaredField(fieldName);
            setField(objBean, field, new String(data.getData()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update field: " + dccValuePath, e);
        }
    }

    /**
     * 将配置的开关字段注册到 zookeeper 配置中心
     * @param bean 对象
     * @param beanName 对象名称
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DCCValue.class)) {
                continue;
            }

            DCCValue dccValue = field.getAnnotation(DCCValue.class);

            String value = dccValue.value();
            if (StringUtils.isBlank(value)) {
                throw new RuntimeException(field.getName() + " @DCCValue is not config value config case 「isSwitch/isSwitch:1」");
            }

            String[] splits = value.split(":");
            String key = splits[0];
            String defaultValue = splits.length == 2 ? splits[1] : null;

            try {
                // 1.判断当前节点是否存在,不存在则创建出 zookeeper 节点
                String keyPath = BASE_CONFIG_PATH_CONFIG.concat("/").concat(key);
                if (null == client.checkExists().forPath(keyPath)) {
                    client.create().creatingParentsIfNeeded().forPath(keyPath);
                    if (StringUtils.isNotBlank(defaultValue)) {
                        setField(bean, field, defaultValue);
                    }
                    log.info("DCC 节点监听 创建节点 {}", keyPath);

                } else {

                    String configValue = new String(client.getData().forPath(keyPath));
                    if (StringUtils.isNotBlank(configValue)) {
                        setField(bean, field, configValue);
                        log.info("DCC 节点监听 设置配置 {} {} {}", keyPath, field.getName(), configValue);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException();
            }

            dccObjGroup.put(BASE_CONFIG_PATH_CONFIG.concat("/").concat(key), bean);

        }

        return bean;

    }

    /**
     * 设置对象的字段值
     * @param bean 对象
     * @param field 字段
     * @param configValue 字段值
     * @throws IllegalAccessException
     */
    private static void setField(Object bean, Field field, String configValue) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(bean, configValue);
        field.setAccessible(false);
    }
}

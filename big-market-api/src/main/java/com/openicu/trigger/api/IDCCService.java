package com.openicu.trigger.api;

import com.openicu.trigger.api.response.Response;

/**
 * @description: DCC 动态配置中心
 * @author: 云奇迹
 * @date: 2024/8/25
 */
public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);

}

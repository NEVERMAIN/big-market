package com.openicu.trigger.api;

import com.openicu.trigger.api.dto.*;
import com.openicu.types.model.Response;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/23
 */
public interface IRaffleActivityService {

    /**
     * 活动装配,数据预热缓存
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * 活动抽奖接口
     * @param request 请求对象
     * @return 返回结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);

    /**
     * 签到返利接口
     * @param userId
     * @return
     */
    Response<Boolean> calendarSignRebate(String userId);

    /**
     * 是否签到
     * @param userId
     * @return
     */
    Response<Boolean> isCalendarSignRebate(String userId);

    /**
     * 查询用户额度接口
     * @param request
     * @return
     */
    Response<UserActivityAccountResponseDTO> queryUserActivityAccount(UserActivityAccountRequestDTO request);
    

}

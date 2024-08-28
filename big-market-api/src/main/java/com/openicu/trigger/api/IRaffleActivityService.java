package com.openicu.trigger.api;

import com.openicu.trigger.api.dto.*;
import com.openicu.trigger.api.response.Response;

import java.math.BigDecimal;
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

    /**
     * 积分兑换商品接口
     * @param request
     * @return
     */
    Response<Boolean> creditPayExchangeSku(SkuProductShopCartRequestDTO request);

    /**
     * 查询活动商品列表接口
     * @param activityId
     * @return
     */
    Response<List<SkuProductResponseDTO>> querySkuProductListByActivityId(Long activityId);

    /**
     * 查询用户积分额度接口
     * @param userId
     * @return
     */
    Response<BigDecimal> queryUserCreditAccount(String userId);

}

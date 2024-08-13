package com.openicu.domain.activity.service.partake;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.openicu.domain.activity.model.entity.ActivityEntity;
import com.openicu.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.openicu.domain.activity.model.entity.UserRaffleOrderEntity;
import com.openicu.domain.activity.model.valobj.ActivityStateVO;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.IRaffleActivityPartakeService;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @description: 标准化流程
 * @author: 云奇迹
 * @date: 2024/7/17
 */
@Slf4j
public abstract class AbstractRaffleActivityPartake implements IRaffleActivityPartakeService {

    protected final IActivityRepository activityRepository;

    public AbstractRaffleActivityPartake(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {

        // 1.基础信息
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        Date currentDay = new Date();

        log.info("创建活动抽奖单开始 userId:{} activityId:{}",userId,activityId);
        // 2.活动查询
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);

        // .校验:活动状态
        if (!ActivityStateVO.open.equals(activityEntity.getState())) {
            log.error("创建活动抽奖单失败，活动状态未开启 activityId:{} state:{}", activityId, activityEntity.getState());
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }

        // .校验:活动日期[开始时间 <- 当前时间 -> 结束时间]
        if (activityEntity.getBeginDateTime().after(currentDay) || activityEntity.getEndDateTime().before(currentDay)) {
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }

        // 3.查询未被使用的活动参与订单记录
        UserRaffleOrderEntity userRaffleOrderEntity = activityRepository.queryNoUsedRaffleOrder(partakeRaffleActivityEntity);
        if (null != userRaffleOrderEntity) {
            log.info("创建参与活动订单存在 userId:{} activityId:{} userRaffleOrderEntity:{}", userId, activityId, JSON.toJSONString(userRaffleOrderEntity));
            return userRaffleOrderEntity;
        }

        // 4.额度账户过滤 & 返回账户构建对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate = this.doFilterAccount(userId, activityId, currentDay);

        // 5.构建订单
        UserRaffleOrderEntity userRaffleOrder = buildUserRaffleOrder(userId, activityId, currentDay);

        // 6. 填充抽奖单实体对象
        createPartakeOrderAggregate.setUserRaffleOrderEntity(userRaffleOrder);

        // 7. 保存聚合对象 - 一个领域内的一个聚合是一个事务操作
        activityRepository.saveCreatePartakeOrderAggregate(createPartakeOrderAggregate);

        log.info("创建活动抽奖单完成 userId:{} activityId:{} orderId:{}", userId, activityId, userRaffleOrder.getOrderId());
        // 8.返回订单信息
        return userRaffleOrder;
    }


    @Override
    public UserRaffleOrderEntity createOrder(String userId, Long activityId) {
        return createOrder(PartakeRaffleActivityEntity.builder()
                .userId(userId)
                .activityId(activityId)
                .build());
    }

    /**
     * 额度账户过滤
     * @param userId 用户ID
     * @param activityId 活动ID
     * @param currentDate 当前时间
     * @return CreatePartakeOrderAggregate
     */
    protected abstract CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate);

    /**
     * 构建用户参与活动的订单
     * @param userId 用户ID
     * @param activityId 活动ID
     * @param currentDate 当前时间
     * @return UserRaffleOrderEntity
     */
    protected abstract UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate);

}

package com.openicu.domain.activity.service.partake;

import com.openicu.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.model.valobj.UserRaffleOrderState;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/17
 */
@Service
@Slf4j
public class RaffleActivityPartakeService extends AbstractRaffleActivityPartake {

    private final SimpleDateFormat dataFormatMonth = new SimpleDateFormat("yyyy-MM");
    private final SimpleDateFormat dataFormatDay = new SimpleDateFormat("yyyy-MM-dd");


    public RaffleActivityPartakeService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

    @Override
    protected CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate) {

        // 1. 查询总账户额度
        ActivityAccountEntity activityAccountEntity = activityRepository.queryActivityAccountByUserId(userId,activityId);

        // 2.额度判断(只判断总剩余额度)
        if(activityAccountEntity != null && activityAccountEntity.getTotalCountSurplus() <= 0){
            throw new AppException(ResponseCode.ACCOUNT_QUOTA_ERROR.getCode(),ResponseCode.ACCOUNT_QUOTA_ERROR.getInfo());
        }

        String month = dataFormatMonth.format(currentDate);
        String day = dataFormatDay.format(currentDate);

        // 3.查询月账户额度
        ActivityAccountMonthEntity activityAccountMonthEntity = activityRepository.queryActivityAccountMonthByUserId(userId,activityId,month);
        if(null != activityAccountMonthEntity && activityAccountMonthEntity.getMonthCountSurplus() <= 0){
            throw new AppException(ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getCode(),ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getInfo());
        }
        // 创建月账户额度: true = 存在月账户额度 false = 不存在月账户额度
        boolean isExistAccountMonth = null != activityAccountMonthEntity;
        if(null == activityAccountMonthEntity){
            activityAccountMonthEntity = new ActivityAccountMonthEntity();
            activityAccountMonthEntity.setUserId(userId);
            activityAccountMonthEntity.setActivityId(activityId);
            activityAccountMonthEntity.setMonth(month);
            activityAccountMonthEntity.setMonthCount(activityAccountEntity.getMonthCount());
            activityAccountMonthEntity.setMonthCountSurplus(activityAccountEntity.getMonthCount());
        }

        // 4.查询日账户额度
        ActivityAccountDayEntity activityAccountDayEntity = activityRepository.queryActivityAccountDayByUserId(userId,activityId,day);
        if(null != activityAccountDayEntity && activityAccountDayEntity.getDayCountSurplus() <= 0){
            throw new AppException(ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getCode(),ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getInfo());
        }

        // 5.创建日账户额度: true = 存在日账户额度 false = 不存在日账户额度
        boolean isExistAccountDay = null != activityAccountDayEntity;
        if(null == activityAccountDayEntity){
            activityAccountDayEntity = new ActivityAccountDayEntity();
            activityAccountDayEntity.setUserId(userId);
            activityAccountDayEntity.setActivityId(activityId);
            activityAccountDayEntity.setDay(day);
            activityAccountDayEntity.setDayCount(activityAccountEntity.getDayCount());
            activityAccountDayEntity.setDayCountSurplus(activityAccountEntity.getDayCount());
        }

        // 6. 构建对象
        return CreatePartakeOrderAggregate.builder()
                .userId(userId)
                .activityId(activityId)
                .activityAccountEntity(activityAccountEntity)
                .isExistAccountMonth(isExistAccountMonth)
                .activityAccountMonthEntity(activityAccountMonthEntity)
                .isExistAccountDay(isExistAccountDay)
                .activityAccountDayEntity(activityAccountDayEntity)
                .build();

    }

    @Override
    protected UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate) {

        // 1.查询活动的详细信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);
        // 2.构建订单
        UserRaffleOrderEntity userRaffleOrderEntity = new UserRaffleOrderEntity();
        userRaffleOrderEntity.setUserId(userId);
        userRaffleOrderEntity.setActivityId(activityId);
        userRaffleOrderEntity.setActivityName(activityEntity.getActivityName());
        userRaffleOrderEntity.setStrategyId(activityEntity.getStrategyId());
        userRaffleOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        userRaffleOrderEntity.setOrderTime(currentDate);
        userRaffleOrderEntity.setOrderState(UserRaffleOrderState.create);
        userRaffleOrderEntity.setEndDateTime(activityEntity.getEndDateTime());

        return userRaffleOrderEntity;
    }
}

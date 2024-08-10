package com.openicu.domain.award.model.aggregate;

import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import com.openicu.domain.award.model.entity.UserCreditAwardEntity;
import com.openicu.domain.award.model.valobj.AwardStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiveOutPrizesAggregate {

    private String userId;

    private UserAwardRecordEntity userAwardRecordEntity;

    private UserCreditAwardEntity userCreditAwardEntity;

    public static UserAwardRecordEntity buildDistributeUserAwardRecordEntity(String userId, String orderId, Integer awardId, AwardStateVO awardState) {

        UserAwardRecordEntity userAwardRecord = new UserAwardRecordEntity();
        userAwardRecord.setUserId(userId);
        userAwardRecord.setOrderId(orderId);
        userAwardRecord.setAwardId(awardId);
        userAwardRecord.setAwardState(awardState);
        return userAwardRecord;

    }

    public static UserCreditAwardEntity buildUserCreditAwardEntity(String userId, BigDecimal creditAmount) {
        return UserCreditAwardEntity.builder()
                .userId(userId)
                .creditAmount(creditAmount)
                .build();
    }


}

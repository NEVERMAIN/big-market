package com.openicu.domain.credit.model.aggregate;

import com.openicu.domain.credit.model.entity.CreditAccountEntity;
import com.openicu.domain.credit.model.entity.CreditOrderEntity;
import com.openicu.domain.credit.model.valobj.TradeNameVO;
import com.openicu.domain.credit.model.valobj.TradeTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeAggregate {

    private String userId;

    private CreditAccountEntity creditAccountEntity;

    private CreditOrderEntity creditOrderEntity;

    public static CreditAccountEntity buildCreditAccountEntity(String userId, BigDecimal amount) {
        return CreditAccountEntity.builder()
                .userId(userId)
                .adjustAmount(amount)
                .build();
    }

    public static CreditOrderEntity buildCreditOrderEntity(String userId, TradeNameVO tradeName, TradeTypeVO tradeType, BigDecimal amount, String outBusinessNo) {
        return CreditOrderEntity.builder()
                .userId(userId)
                .orderId(RandomStringUtils.randomNumeric(12))
                .tradeName(tradeName)
                .tradeType(tradeType)
                .tradeAmount(amount)
                .outBusinessNo(outBusinessNo)
                .build();
    }

}

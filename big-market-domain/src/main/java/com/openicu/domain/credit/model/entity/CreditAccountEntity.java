package com.openicu.domain.credit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class CreditAccountEntity {

    /** 用户ID */
    private String userId;
    /** 调整额度 */
    private BigDecimal adjustAmount;

}

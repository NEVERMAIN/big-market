package com.openicu.domain.award.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.math.BigDecimal;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreditAwardEntity {

    /** 用户ID */
    private String userId;
    /** 用户积分 */
    private BigDecimal creditAmount;

}

package com.openicu.domain.rebate.model.entity;

import com.openicu.domain.rebate.model.valobj.BehaviorTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorEntity {

    /** 用户ID */
    private String userId;
    /** 行为类型: sign-签到 openai_pay-支付 */
    private BehaviorTypeVO behaviorTypeVO;
    /** 业务ID-签到则是日期字符串,支付则是外部的业务ID */
    private String outBusinessNo;

}

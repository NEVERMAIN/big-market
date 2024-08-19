package com.openicu.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 出货单实体对象
 * @author: 云奇迹
 * @date: 2024/8/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderEntity {

    /** 用户ID */
    private String userId;
    /** 业务防重ID - 外部透传,返利,行为等唯一标识 */
    private String outBusinessNo;

}

package com.openicu.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityAccountResponseDTO implements Serializable {

    /** 总次数 */
    private Integer totalCount;
    /** 总次数-剩余 */
    private Integer totalCountSurplus;
    /** 日次数 */
    private Integer dayCount;
    /** 日次数-剩余 */
    private Integer dayCountSurplus;
    /** 月次数 */
    private Integer monthCount;
    /** 月次数-剩余 */
    private Integer monthCountSurplus;
}

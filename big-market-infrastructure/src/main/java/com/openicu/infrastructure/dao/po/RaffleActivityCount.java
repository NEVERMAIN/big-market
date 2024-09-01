package com.openicu.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 抽奖活动次数配置表
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaffleActivityCount {

    /** 自增ID */
    private Long id;
    /** 活动次数编号 */
    private Long activityCountId;
    /** 总次数 */
    private Integer totalCount;
    /** 日次数 */
    private Integer dayCount;
    /** 月次数 */
    private Integer monthCount;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;


}

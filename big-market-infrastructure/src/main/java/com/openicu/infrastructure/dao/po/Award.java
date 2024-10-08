package com.openicu.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 奖品表
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Award {

    /**自增ID */
    private Long id;
    /**抽奖奖品ID-内部流转使用 */
    private Integer awardId;
    /**奖品对接标识 - 每一个都是一个对应的发奖策略 */
    private String awardKey;
    /**奖品配置信息 */
    private String awardConfig;
    /**奖品内容描述 */
    private String awardDesc;
    /**创建时间 */
    private Date createTime;
    /**更新时间 */
    private Date updateTime;
}

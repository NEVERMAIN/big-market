package com.openicu.infrastructure.elasticsearch;

import com.openicu.infrastructure.elasticsearch.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: ES 查询 user_raffle_order表 的接口
 * @author: 云奇迹
 * @date: 2024/9/1
 */
@Mapper
public interface IElasticSearchUserRaffleOrderDao {

    /**
     * 查询用户抽奖订单列表
     * @return
     */
    List<UserRaffleOrder> queryUserRaffleOrderList();

}

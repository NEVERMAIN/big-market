package com.openicu.infrastructure.elasticsearch;

import com.openicu.infrastructure.elasticsearch.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/9/2
 */
@Mapper
public interface IElasticSearchRaffleActivityOrderDao {

    List<RaffleActivityOrder> queryRaffleActivityOrderList();

}

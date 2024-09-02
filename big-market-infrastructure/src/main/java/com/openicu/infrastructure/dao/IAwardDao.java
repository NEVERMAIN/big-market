package com.openicu.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 奖品表 Dao
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Mapper
public interface IAwardDao {

    String queryAwardConfig(Integer awardId);

    String queryAwardKey(Integer awardId);
}

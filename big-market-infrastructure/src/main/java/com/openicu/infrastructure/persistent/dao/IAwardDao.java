package com.openicu.infrastructure.persistent.dao;

import com.openicu.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 奖品表 Dao
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Mapper
public interface IAwardDao {

    /**
     * 查询奖品详细信息
     * @param awardId
     * @return
     */
    Award queryAwardByAwardId(Integer awardId);
}

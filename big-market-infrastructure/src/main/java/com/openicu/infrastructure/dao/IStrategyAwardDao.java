package com.openicu.infrastructure.dao;

import com.openicu.infrastructure.dao.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 策略奖品配置表 Dao
 * @author: 云奇迹
 * @date: 2024/6/14
 */
@Mapper
public interface IStrategyAwardDao {

    /**
     * 通过策略ID查询策略奖励列表。
     *
     * @param strategyId 策略的唯一标识ID。
     * @return 返回匹配该策略ID的奖励列表。
     */
    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    /**
     * 查询策略奖励规则模型。
     *
     * @param strategyAward 包含策略ID和奖励ID的对象。
     * @return 返回策略的奖励规则模型，以字符串形式表示。
     */
    String queryStrategyAwardRuleModels(StrategyAward strategyAward);

    /**
     * 查询策略奖励的剩余数量。
     *
     * @param strategyAward 包含策略ID和奖励ID的对象。
     * @return 返回策略奖励的剩余数量。
     */
    Integer queryStrategyAwardSurplusCount(StrategyAward strategyAward);

    /**
     * 更新策略奖励的库存。
     *
     * @param strategyAward 包含策略ID、奖励ID和新的库存数量的对象。
     * 此方法用于更新数据库中相应策略奖励的库存数量。
     */
    void updateStrategyAwardStock(StrategyAward strategyAward);

    /**
     * 清空策略奖励的库存。
     *
     * @param strategyAward 包含策略ID和奖励ID的对象。
     * 此方法用于清空数据库中相应策略奖励的库存数量。
     */
    void clearStrategyAwardStock(StrategyAward strategyAward);

    /**
     * 查询策略奖品
     * @param strategyAwardReq
     * @return
     */
    StrategyAward queryStrategyAward(StrategyAward strategyAwardReq);

    /**
     * 查询开启的策略奖品列表
     * @return
     */
    List<StrategyAward> queryOpenActivityStrategyAwardList();

}

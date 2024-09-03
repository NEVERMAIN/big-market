package com.openicu.domain.strategy.resposity;

import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;
import com.openicu.domain.strategy.model.valobj.RuleTreeVO;
import com.openicu.domain.strategy.model.valobj.RuleWeightVO;
import com.openicu.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.openicu.domain.strategy.model.entity.StrategyEntity;
import com.openicu.domain.strategy.model.entity.StrategyRuleEntity;
import com.openicu.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description: 策略仓储接口
 * @author: 云奇迹
 * @date: 2024/6/15
 */
public interface IStrategyRepository {


    /**
     * 根据策略ID查询策略奖品列表。
     *
     * @param strategyId 策略的唯一标识ID。
     * @return 返回策略奖品的实体列表。
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 存储策略奖品搜索率表格。
     *
     * @param key 表格的唯一标识键。
     * @param rateRange 等级范围，用于区分不同的搜索率等级。
     * @param shuffleStrategyAwardSearchRateTables 包含等级和对应搜索率的映射表。
     */
    void storeStrategyAwardSearchRateTable(String key, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTables);

    /**
     * 根据键和等级键获取策略奖品的组装结果。
     *
     * @param key 奖品表格的唯一标识键。
     * @param rateKey 等级键，用于查询特定等级的奖品。
     * @return 返回策略奖品的组装结果。
     */
    Integer getStrategyAwardAssemble(String key,Integer rateKey);

    /**
     * 根据键获取等级范围。
     *
     * @param key 唯一标识键。
     * @return 返回等级范围。
     */
    int getRateRange(String key);

    /**
     * 根据策略ID查询策略实体。
     *
     * @param strategyId 策略的唯一标识ID。
     * @return 返回策略的实体信息。
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 根据策略ID和规则模型查询策略规则实体。
     *
     * @param strategyId 策略的唯一标识ID。
     * @param ruleModel 规则模型的标识。
     * @return 返回策略规则的实体信息。
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    /**
     * 根据策略ID、奖品ID和规则模型查询策略规则的值。
     *
     * @param strategyId 策略的唯一标识ID。
     * @param awardId 奖品的唯一标识ID。
     * @param ruleModel 规则模型的标识。
     * @return 返回策略规则的值。
     */
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    /**
     * 根据策略ID和奖品ID查询策略奖品规则模型的视图对象。
     *
     * @param strategyId 策略的唯一标识ID。
     * @param awardId 奖品的唯一标识ID。
     * @return 返回包含策略奖品规则模型的视图对象。
     */
    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);

    /**
     * 根据策略ID和规则模型查询策略规则值。
     *
     * @param strategyId 策略的唯一标识ID，用于定位特定的策略。
     * @param ruleModel 规则模型或类型，用于指定查询的规则。
     * @return 查询到的策略规则值，以字符串形式返回。
     */
    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    /**
     * 根据规则树ID，查询树结构信息
     *
     * @param ruleModels 规则模型或类型，用于指定查询的规则。
     * @return 查询到的规则树视图对象。
     */
    RuleTreeVO queryRuleTreeVOByTreeId(String ruleModels);

    /**
     * 根据策略ID和奖品ID查询剩余奖品数量。
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @return
     */
    Integer queryStrategyAwardSurplusCount(Long strategyId, Integer awardId);

    /**
     * 缓存奖品库存
     * @param cacheKey 缓存Key
     * @param awardCount 奖品库存值
     */
    void cacheStrategyAwardCount(String cacheKey,Integer awardCount);

    /**
     * 缓存key，decr 方式扣减库存
     *
     * @param cacheKey 缓存Key
     * @param endDateTime 活动结束时间
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId,String cacheKey, Date endDateTime);


    /**
     * 写入奖品库存到消费队列
     * @param strategyAwardStockKeyVO 对象值对象
     */
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);

    /**
     * 获取奖品库存消费队列
     * @return StrategyAwardStockKeyVO
     * @throws InterruptedException 打断异常
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 根据策略ID+奖品ID 获取指定的奖品库存消耗队列
     * @param strategyId
     * @param awardId
     * @return
     * @throws InterruptedException
     */
    StrategyAwardStockKeyVO takeQueueValue(Long strategyId,Integer awardId) throws InterruptedException;

    /**
     * 更新奖品库存消耗
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     */
    void updateStrategyAwardStock(Long strategyId,Integer awardId);

    /**
     * 清空队列
     */
    void clearQueueValue();

    /**
     * 清空数据库 抽奖奖品库存
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     */
    void clearStrategyAwardStock(Long strategyId, Integer awardId);

    /**
     * 查询策略ID
     * @param activityId
     * @return
     */
    Long queryStrategyIdByActivityId(Long activityId);

    /**
     * 查询用户抽奖次数 - 当天的；策略ID:活动ID 1:1 的配置，可以直接用 strategyId 查询。
     * @param userId
     * @param strategyId
     * @return
     */
    Integer queryTodayUserRaffleCount(String userId, Long strategyId);

    /**
     * 查询策略奖品实体
     * @param strategyId
     * @param awardId
     * @return
     */
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);

    /**
     * 根据规则树ID集合查询奖品中加锁数量的配置「部分奖品需要抽奖N次解锁」
     *
     * @param treeIds 规则树ID值
     * @return key 规则树，value rule_lock 加锁值
     */
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);

    Long queryActivitySkuByActivityId(Long activityId);

    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);

    /**
     * 查询用户总抽奖次数
     * @param userId
     * @param strategyId
     * @return
     */
    Integer queryActivityAccountTotalUseCount(String userId, Long strategyId);

    List<StrategyAwardStockKeyVO> queryOpenActivityStrategyAwardList();

}

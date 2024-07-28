package com.openicu.infrastructure.persistent.repository;

import com.openicu.domain.strategy.event.StrategyAwardStockZeroMessageEvent;
import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;
import com.openicu.domain.strategy.model.entity.StrategyEntity;
import com.openicu.domain.strategy.model.entity.StrategyRuleEntity;
import com.openicu.domain.strategy.model.valobj.*;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import com.openicu.infrastructure.event.EventPublisher;
import com.openicu.infrastructure.persistent.dao.*;
import com.openicu.infrastructure.persistent.po.*;
import com.openicu.infrastructure.persistent.redis.IRedisService;
import com.openicu.types.common.Constants;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: 策略仓储实现
 * @author: 云奇迹
 * @date: 2024/6/15
 */
@Slf4j
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Resource
    private IRaffleActivityAccountDayDao raffleActivityAccountDayDao;

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Resource
    private IRuleTreeDao ruleTreeDao;

    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;

    @Resource
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Resource
    private IRedisService redisService;

    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private StrategyAwardStockZeroMessageEvent strategyAwardStockZeroMessageEvent;


    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {

        // 1.优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_LIST_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (null != strategyAwardEntities && !strategyAwardEntities.isEmpty()) return strategyAwardEntities;
        // 2.从库中获取数据
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardTitle(strategyAward.getAwardTitle())
                    .awardSubtitle(strategyAward.getAwardSubtitle())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .ruleModels(strategyAward.getRuleModels())
                    .sort(strategyAward.getSort())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;

    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTables) {
        // 1. 存储抽奖策略范围,如 10000 ,用于生成 10000 以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTables);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public int getRateRange(String key) {
        String cacheKey = Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key;
        if (!redisService.isExists(cacheKey)) {
            throw new AppException(ResponseCode.UN_ASSEMBLED_STRATEGY_ARMORY.getCode(),
                    ResponseCode.UN_ASSEMBLED_STRATEGY_ARMORY.getInfo());
        }
        return redisService.getValue(cacheKey);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 1.优先从缓存获取
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (null != strategyEntity) return strategyEntity;
        // 2.从数据库中查询数据
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
        // 3. 缓存数据
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {

        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);

        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleRes.getStrategyId())
                .awardId(strategyRuleRes.getAwardId())
                .ruleType(strategyRuleRes.getRuleType())
                .ruleModel(strategyRuleRes.getRuleModel())
                .ruleValue(strategyRuleRes.getRuleValue())
                .ruleDesc(strategyRuleRes.getRuleDesc())
                .build();

    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModels(strategyAward);
        return StrategyAwardRuleModelVO.builder().ruleModels(ruleModels).build();
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId, null, ruleModel);
    }

    @Override
    public RuleTreeVO queryRuleTreeVOByTreeId(String treeId) {

        // 1.优先从缓存获取
        String cacheKey = Constants.RedisKey.RULE_TREE_VO_KEY + treeId;
        RuleTreeVO ruleTreeVOCache = redisService.getValue(cacheKey);
        if (null != ruleTreeVOCache) return ruleTreeVOCache;

        // 2.从数据库获取
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNode> ruleTreeNodeList = ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLine> ruleTreeNodeLineList = ruleTreeNodeLineDao.queryRuleTreeNodeLineListByTreeId(treeId);

        // 3. tree_node_line 转换成 map 结构
        Map<String, List<RuleTreeNodeLineVO>> ruleTreeNodeLineMap = new HashMap<>();
        for (RuleTreeNodeLine ruleTreeNodeLine : ruleTreeNodeLineList) {
            RuleTreeNodeLineVO ruleTreeNodeLineVO = RuleTreeNodeLineVO.builder()
                    .treeId(ruleTreeNodeLine.getTreeId())
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();

            List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList = ruleTreeNodeLineMap.computeIfAbsent(ruleTreeNodeLine.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVOList.add(ruleTreeNodeLineVO);
        }

        // 4. tree_node 转成 map 结构
        Map<String, RuleTreeNodeVO> treeNodeMap = new HashMap<>();
        for (RuleTreeNode ruleTreeNode : ruleTreeNodeList) {
            RuleTreeNodeVO ruleTreeNodeVO = RuleTreeNodeVO.builder()
                    .treeId(ruleTreeNode.getTreeId())
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeLineMap.get(ruleTreeNode.getRuleKey()))
                    .build();

            treeNodeMap.put(ruleTreeNode.getRuleKey(), ruleTreeNodeVO);
        }

        // 5. 构建 Rule_Tree 结构
        RuleTreeVO ruleTreeVODB = RuleTreeVO.builder()
                .treeId(ruleTree.getTreeId())
                .treeName(ruleTree.getTreeName())
                .treeDesc(ruleTree.getTreeDesc())
                .treeRootRuleNode(ruleTree.getTreeRootRuleKey())
                .treeNodeMap(treeNodeMap)
                .build();

        redisService.setValue(cacheKey, ruleTreeVODB);
        return ruleTreeVODB;
    }

    @Override
    public Integer queryStrategyAwardSurplusCount(Long strategyId, Integer awardId) {

        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        return strategyAwardDao.queryStrategyAwardSurplusCount(strategyAward);
    }

    @Override
    public void cacheStrategyAwardCount(String cacheKey, Integer awardCount) {
        if (redisService.isExists(cacheKey)) return;
        redisService.setAtomicLong(cacheKey, awardCount);
    }


    @Override
    public Boolean subtractionAwardStock(Long strategyId, Integer awardId, String cacheKey, Date endDateTime) {

        long surplus = redisService.decr(cacheKey);
        if (surplus == 0) {
            // 发送消息给MQ,清空延迟队列
            eventPublisher.publish(strategyAwardStockZeroMessageEvent.topic(),
                    strategyAwardStockZeroMessageEvent.buildEventMessage(StrategyAwardStockKeyVO.builder()
                            .strategyId(strategyId)
                            .awardId(awardId)
                            .build()));
        }
        if (surplus < 0) {
            // 库存小于0，恢复为0个
            redisService.setValue(cacheKey, 0);
            return false;
        }

        // 1. 按照cacheKey decr 后的值，如 99、98、97 和 key 组成为库存锁的key进行使用。
        // 2. 加锁为了兜底，如果后续有恢复库存，手动处理等，也不会超卖。因为所有的可用库存key，都被加锁了。
        String lockKey = cacheKey + Constants.UNDERLINE + surplus;
        Boolean lock = false;
        if (null != endDateTime) {
            long expireMills = endDateTime.getTime() - System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
            lock = redisService.setNx(lockKey, expireMills, TimeUnit.MILLISECONDS);
        } else {
            lock = redisService.setNx(lockKey);
        }
        if (!lock) {
            log.info("策略奖品库存加锁失败 {}", lockKey);
        }
        return lock;
    }


    /**
     * 将策略奖品库存键值对象放入延迟队列中。
     */
    @Override
    public void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUERY_KEY + strategyAwardStockKeyVO.getSku();
        // 从Redis服务中获取指定缓存键对应的阻塞队列。
        RBlockingQueue<StrategyAwardStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        // 获取阻塞队列对应的延迟队列，用于实现延迟处理功能。
        RDelayedQueue<StrategyAwardStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        // 将策略奖品库存键值对象加入延迟队列，并设置延迟时间为3秒。
        delayedQueue.offer(strategyAwardStockKeyVO, 3, TimeUnit.SECONDS);
    }


    /**
     * 从指定的Redis阻塞队列中获取策略奖品库存键值对象。
     */
    @Override
    public StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUERY_KEY;
        // 通过缓存键值获取对应的Redis阻塞队列。
        RBlockingQueue<StrategyAwardStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        // 从阻塞队列中获取并移除一个元素，如果队列为空，则会阻塞直到有元素可用。
        return destinationQueue.poll();
    }




    @Override
    public void clearQueueValue() {

        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUERY_KEY;
        // 通过缓存键获取阻塞队列
        RBlockingQueue<Object> destinationQueue = redisService.getBlockingQueue(cacheKey);
        // 清空阻塞队列
        destinationQueue.clear();
    }


    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        strategyAwardDao.updateStrategyAwardStock(strategyAward);
    }

    @Override
    public void clearStrategyAwardStock(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        strategyAwardDao.clearStrategyAwardStock(strategyAward);
    }

    @Override
    public Long queryStrategyIdByActivityId(Long activityId) {

        return raffleActivityDao.queryStrategyIdByActivityId(activityId);

    }

    @Override
    public Integer queryTodayUserRaffleCount(String userId, Long strategyId) {

        // 1.查询活动ID
        Long activityId = raffleActivityDao.queryActivityIdByStrategyId(strategyId);
        // 2.查询用户今天参与活动次数
        RaffleActivityAccountDay raffleActivityAccountDayReq = new RaffleActivityAccountDay();
        raffleActivityAccountDayReq.setUserId(userId);
        raffleActivityAccountDayReq.setActivityId(activityId);
        raffleActivityAccountDayReq.setDay(raffleActivityAccountDayReq.currentDay());
        RaffleActivityAccountDay raffleActivityAccountDay =
                raffleActivityAccountDayDao.queryActivityAccountDayByUserId(raffleActivityAccountDayReq);
        if (null == raffleActivityAccountDay) return 0;
        // 总次数 - 剩余次数 = 今天已参与次数
        return raffleActivityAccountDay.getDayCount() - raffleActivityAccountDay.getDayCountSurplus();
    }

    @Override
    public StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId) {

        // 1.优先从缓存获取
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId + Constants.UNDERLINE + awardId;
        StrategyAwardEntity strategyAwardEntity = redisService.getValue(cacheKey);
        if (null != strategyAwardEntity) return strategyAwardEntity;

        // 2.从数据库中查询数据
        StrategyAward strategyAwardReq = new StrategyAward();
        strategyAwardReq.setStrategyId(strategyId);
        strategyAwardReq.setAwardId(awardId);
        StrategyAward strategyAward = strategyAwardDao.queryStrategyAward(strategyAwardReq);

        strategyAwardEntity = StrategyAwardEntity.builder()
                .strategyId(strategyAward.getStrategyId())
                .awardId(strategyAward.getAwardId())
                .awardTitle(strategyAward.getAwardTitle())
                .awardCount(strategyAward.getAwardCount())
                .awardCountSurplus(strategyAward.getAwardCountSurplus())
                .awardRate(strategyAward.getAwardRate())
                .sort(strategyAward.getSort())
                .build();

        // 3.缓存结果
        redisService.setValue(cacheKey, strategyAwardEntity);
        // 4.返回结果
        return strategyAwardEntity;
    }

    @Override
    public Map<String, Integer> queryAwardRuleLockCount(String[] treeIds) {
        if (null == treeIds || treeIds.length == 0) return new HashMap<>();
        List<RuleTreeNode> ruleTreeNodeList = ruleTreeNodeDao.queryRuleLocks(treeIds);
        HashMap<String, Integer> resultMap = new HashMap<>();
        for (RuleTreeNode node : ruleTreeNodeList) {
            String treeId = node.getTreeId();
            Integer ruleValue = Integer.parseInt(node.getRuleValue());
            resultMap.put(treeId, ruleValue);
        }
        return resultMap;
    }

    @Override
    public Long queryActivitySkuByActivityId(Long activityId) {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySkuByActivityId(activityId);
        return raffleActivitySku.getSku();
    }
}

package com.openicu.infrastructure.persistent.repository;

import com.alibaba.fastjson.JSON;
import com.myapp.middleware.db.router.strategy.IDBRouterStrategy;
import com.openicu.domain.activity.event.ActivitySkuStockZeroMessageEvent;
import com.openicu.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.openicu.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.openicu.domain.activity.model.valobj.ActivityStateVO;
import com.openicu.domain.activity.model.valobj.OrderStateVO;
import com.openicu.domain.activity.model.valobj.UserRaffleOrderState;
import com.openicu.domain.activity.repository.IActivityRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description: 活动仓储服务
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Slf4j
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;

    @Resource
    private IRaffleActivityAccountDao raffleActivityAccountDao;

    @Resource
    private IRaffleActivityAccountMonthDao raffleActivityAccountMonthDao;

    @Resource
    private IRaffleActivityAccountDayDao raffleActivityAccountDayDao;

    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private ActivitySkuStockZeroMessageEvent activitySkuStockZeroMessageEvent;

    @Resource
    private IUserRaffleOrderDao userRaffleOrderDao;


    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        // 1.从数据库中查询活动库存
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        Long cacheSkuStock = redisService.getAtomicLong(cacheKey);
        if (null == cacheSkuStock || 0 == cacheSkuStock) {
            cacheSkuStock = 0L;
        }
        // 返回活动商品sku信息
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(cacheSkuStock.intValue())
                .build();
    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {

        // 1. 优先从缓存中获取
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (null != activityEntity) return activityEntity;
        // 2. 从库中获取数据
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .strategyId(raffleActivity.getStrategyId())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        // 3. 缓存数据
        redisService.setValue(cacheKey, activityEntity);
        return activityEntity;

    }

    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {

        // 1. 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;
        // 2. 从数据库中获取数据
        RaffleActivityCount raffleActivityCount =
                raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .dayCount(raffleActivityCount.getDayCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .build();
        // 3. 缓存数据
        redisService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
    }

    @Override
    public void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate) {
        try {
            // 1.订单对象
            ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
            RaffleActivityOrder raffleActivityOrder = RaffleActivityOrder.builder()
                    .userId(activityOrderEntity.getUserId())
                    .sku(activityOrderEntity.getSku())
                    .activityId(activityOrderEntity.getActivityId())
                    .activityName(activityOrderEntity.getActivityName())
                    .strategyId(activityOrderEntity.getStrategyId())
                    .orderId(activityOrderEntity.getOrderId())
                    .orderTime(activityOrderEntity.getOrderTime())
                    .totalCount(activityOrderEntity.getTotalCount())
                    .dayCount(activityOrderEntity.getDayCount())
                    .monthCount(activityOrderEntity.getMonthCount())
                    .state(activityOrderEntity.getState().getCode())
                    .outBusinessNo(activityOrderEntity.getOutBusinessNo())
                    .build();

            // 2. 账户对象
            RaffleActivityAccount raffleActivityAccount = RaffleActivityAccount.builder()
                    .userId(createOrderAggregate.getUserId())
                    .activityId(createOrderAggregate.getActivityId())
                    .totalCount(createOrderAggregate.getTotalCount())
                    .totalCountSurplus(createOrderAggregate.getTotalCount())
                    .dayCount(createOrderAggregate.getDayCount())
                    .dayCountSurplus(createOrderAggregate.getDayCount())
                    .monthCount(createOrderAggregate.getMonthCount())
                    .monthCountSurplus(createOrderAggregate.getMonthCount())
                    .build();

            // 3. 以用户ID作为切分键,通过 doRouter 设定路由【这样就保证了下面的操作,都是同一个连接下的,保证了事务的特性】
            dbRouter.doRouter(createOrderAggregate.getUserId());
            // 编程式事务
            transactionTemplate.execute(status -> {
                try {
                    // 1.写入订单
                    raffleActivityOrderDao.insert(raffleActivityOrder);
                    // 2.更新账户
                    // todo 这里更新用户账户的逻辑
                    int count = raffleActivityAccountDao.updateAccountQuota(raffleActivityAccount);
                    // 3.创建账户 更新为0,则账户不存在,创建新账户
                    if (0 == count) {
                        raffleActivityAccountDao.insert(raffleActivityAccount);
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入订单记录,唯一索引冲突 userId:{} activityId:{} sku:{}", activityOrderEntity.getUserId(), activityOrderEntity.getActivityId(), activityOrderEntity.getSku());
                    throw new AppException(ResponseCode.INDEX_DUP.getCode());
                }
            });
        } finally {
            dbRouter.clear();
        }
    }

    @Override
    public void cacheActivitySkuStockCount(String cacheKey, Integer stockCount) {
        // 1.如果已经缓存,则直接返回
        if (redisService.isExists(cacheKey)) return;
        // 2.缓存活动商品库存
        redisService.setAtomicLong(cacheKey, stockCount);
    }

    @Override
    public boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime) {

        long surplus = redisService.decr(cacheKey);
        if (surplus == 0) {
            // 库存消耗完以后,发送MQ消息,更新数据库库存。设置商品库存为0
            eventPublisher.publish(activitySkuStockZeroMessageEvent.topic(), activitySkuStockZeroMessageEvent.buildEventMessage(sku));
        } else if (surplus < 0) {
            // 库存小于 0 , 恢复为 0 个
            redisService.setAtomicLong(cacheKey, 0);
            return false;
        }

        // 1.按照 cacheKey decr 后的值,如 99,98,97 和 key 组成的库存锁的 key 进行使用
        // 2.加锁为了兜底,如果后续有恢复库存,手动处理等【运营是人来操作,会有这种情况发送,系统要做防护】,也不会超卖.因为所有的可用库存key,都被加锁了。
        // 3.设置加锁时间为活动到期 + 延迟1天
        String lockKey = cacheKey + Constants.UNDERLINE + surplus;
        long expireMills = endDateTime.getTime() - System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
        boolean lock = redisService.setNx(lockKey, expireMills, TimeUnit.MILLISECONDS);
        if (!lock) {
            log.info("活动 sku 库存加锁失败 lockKey:{}", lockKey);
        }
        return lock;
    }

    @Override
    public void activitySkuStockConsumerSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO) {
        // 1.获取 sku 的延迟队列
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY + activitySkuStockKeyVO.getSku();
        RBlockingQueue<ActivitySkuStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<ActivitySkuStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(activitySkuStockKeyVO, 3, TimeUnit.SECONDS);
    }

    @Override
    public ActivitySkuStockKeyVO takeQueueValue() {

        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        RBlockingQueue<ActivitySkuStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        return destinationQueue.poll();

    }

    @Override
    public ActivitySkuStockKeyVO takeQueueValue(Long sku) {

        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY + sku;
        RBlockingQueue<ActivitySkuStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        return destinationQueue.poll();

    }

    @Override
    public void clearQueueValue() {

        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY;
        RBlockingQueue<ActivitySkuStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        destinationQueue.clear();
    }

    @Override
    public void clearQueueValue(Long sku) {

        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY + sku;
        RBlockingQueue<Object> destinationQueue = redisService.getBlockingQueue(cacheKey);
        destinationQueue.clear();

    }

    @Override
    public void updateActivitySkuStock(Long sku) {

        raffleActivitySkuDao.updateActivitySkuStock(sku);

    }

    @Override
    public void clearActivitySkuStock(Long sku) {
        raffleActivitySkuDao.clearActivitySkuStock(sku);

    }

    @Override
    public UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        // 1.从数据库查询未消费的订单
        UserRaffleOrder userRaffleOrder = userRaffleOrderDao.queryNoUsedRaffleOrder(userId, activityId);
        if (null == userRaffleOrder) return null;
        // 2.构建用户抽奖订单的实体对象
        return UserRaffleOrderEntity.builder()
                .userId(userRaffleOrder.getUserId())
                .activityId(userRaffleOrder.getActivityId())
                .activityName(userRaffleOrder.getActivityName())
                .strategyId(userRaffleOrder.getStrategyId())
                .orderId(userRaffleOrder.getOrderId())
                .orderTime(userRaffleOrder.getOrderTime())
                .orderState(UserRaffleOrderState.valueOf(userRaffleOrder.getOrderState()))
                .build();
    }

    @Override
    public ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId) {

        // 1. 查询账户
        RaffleActivityAccount raffleActivityAccountReq = new RaffleActivityAccount();
        raffleActivityAccountReq.setUserId(userId);
        raffleActivityAccountReq.setActivityId(activityId);
        RaffleActivityAccount raffleActivityAccountRes = raffleActivityAccountDao.queryActivityAccountByUserId(raffleActivityAccountReq);
        if (null == raffleActivityAccountRes) return null;
        // 2. 转换对象
        return ActivityAccountEntity.builder()
                .userId(raffleActivityAccountRes.getUserId())
                .activityId(raffleActivityAccountRes.getActivityId())
                .totalCount(raffleActivityAccountRes.getTotalCount())
                .totalCountSurplus(raffleActivityAccountRes.getTotalCountSurplus())
                .dayCount(raffleActivityAccountRes.getDayCount())
                .dayCountSurplus(raffleActivityAccountRes.getDayCountSurplus())
                .monthCount(raffleActivityAccountRes.getMonthCount())
                .monthCountSurplus(raffleActivityAccountRes.getMonthCountSurplus())
                .build();


    }

    @Override
    public ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month) {

        // 1.从数据库查询数据
        RaffleActivityAccountMonth req = new RaffleActivityAccountMonth();
        req.setActivityId(activityId);
        req.setUserId(userId);
        req.setMonth(month);
        RaffleActivityAccountMonth raffleActivityAccountMonthRes = raffleActivityAccountMonthDao.queryActivityAccountMonthByUserId(req);
        // 2.判空
        if (null == raffleActivityAccountMonthRes) return null;
        // 3.构建并返回实体化对象对象
        return ActivityAccountMonthEntity.builder()
                .userId(raffleActivityAccountMonthRes.getUserId())
                .activityId(raffleActivityAccountMonthRes.getActivityId())
                .month(raffleActivityAccountMonthRes.getMonth())
                .monthCount(raffleActivityAccountMonthRes.getMonthCount())
                .monthCountSurplus(raffleActivityAccountMonthRes.getMonthCountSurplus())
                .build();

    }

    @Override
    public ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day) {

        // 1.从数据库查询数据
        RaffleActivityAccountDay req = new RaffleActivityAccountDay();
        req.setUserId(userId);
        req.setActivityId(activityId);
        req.setDay(day);
        RaffleActivityAccountDay raffleActivityAccountDayRes = raffleActivityAccountDayDao.queryActivityAccountDayByUserId(req);
        // 2.判空
        if (null == raffleActivityAccountDayRes) return null;
        // 3.构建并返回实体化对象对象
        return ActivityAccountDayEntity.builder()
                .userId(raffleActivityAccountDayRes.getUserId())
                .activityId(raffleActivityAccountDayRes.getActivityId())
                .day(raffleActivityAccountDayRes.getDay())
                .dayCount(raffleActivityAccountDayRes.getDayCount())
                .dayCountSurplus(raffleActivityAccountDayRes.getDayCountSurplus())
                .build();
    }

    @Override
    public void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate) {

        String userId = createPartakeOrderAggregate.getUserId();
        Long activityId = createPartakeOrderAggregate.getActivityId();
        ActivityAccountDayEntity activityAccountDayEntity = createPartakeOrderAggregate.getActivityAccountDayEntity();
        ActivityAccountMonthEntity activityAccountMonthEntity = createPartakeOrderAggregate.getActivityAccountMonthEntity();
        UserRaffleOrderEntity userRaffleOrderEntity = createPartakeOrderAggregate.getUserRaffleOrderEntity();

        // 统一切换路由,以下事务内的所有操作,都走一个路由
        dbRouter.doRouter(userId);
        // 在同一个事务下完成日账户、月账户、用户抽奖单的操作
        transactionTemplate.execute(status -> {

            try {

                // 1.更新总账户
                int totalCount = raffleActivityAccountDao.updateActivityAccountSubtractionQuota(
                        RaffleActivityAccount.builder()
                                .userId(userId)
                                .activityId(activityId)
                                .build()
                );
                if (1 != totalCount) {
                    status.setRollbackOnly();
                    log.warn("写入创建参与活动记录,更新总账户额度不足,抛出异常 userId:{} activityId:{}", userId, activityId);
                    throw new AppException(ResponseCode.ACCOUNT_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_QUOTA_ERROR.getInfo());
                }

                // 2.创建或更新月账户,true - 更新,false - 创建
                if (createPartakeOrderAggregate.isExistAccountMonth()) {

                    // 2.1. 更新月账户额度
                    int updateMonthCount = raffleActivityAccountMonthDao.updateActivityAccountMonthSubtractionQuota(
                            RaffleActivityAccountMonth.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .month(activityAccountMonthEntity.getMonth())
                                    .build()
                    );

                    if (1 != updateMonthCount) {
                        // 未更新成功则回滚
                        status.setRollbackOnly();
                        log.warn("写入创建参与活动记录，更新月账户额度不足，异常 userId: {} activityId: {} month: {}", userId, activityId, activityAccountMonthEntity.getMonth());
                        throw new AppException(ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getInfo());
                    }

                } else {

                    // 2.2.创建月账户
                    raffleActivityAccountMonthDao.insertActivityAccountMonth(
                            RaffleActivityAccountMonth.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .month(activityAccountMonthEntity.getMonth())
                                    .monthCount(activityAccountMonthEntity.getMonthCount())
                                    .monthCountSurplus(activityAccountMonthEntity.getMonthCountSurplus() - 1)
                                    .build()
                    );

                    // 新创建日账户,则更新总账表中日次数的镜像额度
                    raffleActivityAccountDao.updateActivityAccountMonthSurplusImageQuota(
                            RaffleActivityAccount.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .monthCountSurplus(activityAccountMonthEntity.getMonthCountSurplus())
                                    .build()
                    );

                }

                // 3.创建或更新日账户,true - 更新,false - 创建
                if (createPartakeOrderAggregate.isExistAccountDay()) {

                    // 3.1. 更新日次数余额
                    int updateDayCount = raffleActivityAccountDayDao.updateActivityAccountMonthSubtractionQuota(
                            RaffleActivityAccountDay.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .day(activityAccountDayEntity.getDay())
                                    .build()
                    );

                    if (1 != updateDayCount) {
                        // 未更新成功则回滚
                        status.setRollbackOnly();
                        log.warn("写入创建参与活动记录，更新日账户额度不足，异常 userId: {} activityId: {} day: {}", userId, activityId, activityAccountDayEntity.getDay());
                        throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
                    }

                } else {

                    // 3.2 创建日次数余额
                    raffleActivityAccountDayDao.insertActivityAccountDay(
                            RaffleActivityAccountDay.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .day(activityAccountDayEntity.getDay())
                                    .dayCount(activityAccountDayEntity.getDayCount())
                                    .dayCountSurplus(activityAccountDayEntity.getDayCountSurplus() - 1)
                                    .build()
                    );

                    // 新创建日账户,则更新总账表中日次数的镜像额度
                    raffleActivityAccountDao.updateActivityAccountDaySurplusImageQuota(
                            RaffleActivityAccount.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .dayCountSurplus(activityAccountMonthEntity.getMonthCountSurplus())
                                    .build()
                    );

                }

                // 4. 写入参与活动订单
                userRaffleOrderDao.insert(
                        UserRaffleOrder.builder()
                                .userId(userRaffleOrderEntity.getUserId())
                                .activityId(userRaffleOrderEntity.getActivityId())
                                .activityName(userRaffleOrderEntity.getActivityName())
                                .strategyId(userRaffleOrderEntity.getStrategyId())
                                .orderId(userRaffleOrderEntity.getOrderId())
                                .orderTime(userRaffleOrderEntity.getOrderTime())
                                .orderState(userRaffleOrderEntity.getOrderState().getCode())
                                .build()
                );

                return 1;

            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
                log.error("写入创建参与活动记录，唯一索引冲突 userId:{} activityId:{}", userId, activityId, e);
                throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
            } finally {
                // 清空 threadLocal
                dbRouter.clear();
            }
        });

    }

    @Override
    public List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId) {

        // 1. 查询数据库
        List<RaffleActivitySku> raffleActivitySkuList = raffleActivitySkuDao.queryActivitySkuListByActivityId(activityId);
        // 2.判断空
        if (raffleActivitySkuList.isEmpty()) return null;
        // 3.转成实体化对象
        return raffleActivitySkuList.stream()
                .map(entity -> ActivitySkuEntity.builder()
                        .activityId(entity.getActivityId())
                        .sku(entity.getSku())
                        .activityCountId(entity.getActivityCountId())
                        .stockCount(entity.getStockCount())
                        .stockCountSurplus(entity.getStockCountSurplus())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public Integer queryRaffleActivityAccountDayPartakeCount(String userId, Long activityId) {
        RaffleActivityAccountDay raffleActivityAccountDay = new RaffleActivityAccountDay();
        raffleActivityAccountDay.setUserId(userId);
        raffleActivityAccountDay.setActivityId(activityId);
        raffleActivityAccountDay.setDay(raffleActivityAccountDay.currentDay());
        Integer dayPartakeCount = raffleActivityAccountDayDao.queryRaffleActivityAccountDayPartakeCount(raffleActivityAccountDay);
        // 当日未参与抽奖则为0次
        return null == dayPartakeCount ? 0 : dayPartakeCount;
    }

    @Override
    public List<Long> querySkuList() {

        // 1.优先从缓存中获取
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_LIST_KEY;
        String jsonList = redisService.getValue(cacheKey);
        List<Long> resultList = JSON.parseArray(jsonList, Long.class);
        if (null != jsonList && null != resultList && !resultList.isEmpty()) return resultList;
        // 从数据库中查询
        List<RaffleActivitySku> raffleActivitySkuList = raffleActivitySkuDao.querySkuList();
        resultList = raffleActivitySkuList.stream().map(RaffleActivitySku::getSku).collect(Collectors.toList());
        redisService.setValue(cacheKey, JSON.toJSONString(resultList));
        return resultList;
    }


}

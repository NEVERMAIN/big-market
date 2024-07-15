package com.openicu.infrastructure.persistent.repository;

import com.myapp.middleware.db.router.strategy.IDBRouterStrategy;
import com.openicu.domain.activity.event.ActivitySkuStockZeroMessageEvent;
import com.openicu.domain.activity.model.aggregate.CreateOrderAggregate;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.openicu.domain.activity.model.valobj.ActivityStateVO;
import com.openicu.domain.activity.model.valobj.OrderStateVO;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.infrastructure.event.EventPublisher;
import com.openicu.infrastructure.persistent.dao.*;
import com.openicu.infrastructure.persistent.po.*;
import com.openicu.infrastructure.persistent.redis.IRedisService;
import com.openicu.types.common.Constants;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import com.openicu.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private TransactionTemplate transactionTemplate;
    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private ActivitySkuStockZeroMessageEvent activitySkuStockZeroMessageEvent;



    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        // 1.从数据库中查询活动库存
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        Long cacheSkuStock = redisService.getAtomicLong(cacheKey);
        if(null == cacheSkuStock || 0 == cacheSkuStock){
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
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
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
    public void doSaveOrder(CreateOrderAggregate createOrderAggregate) {
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
               try{
                   // 1.写入订单
                   raffleActivityOrderDao.insert(raffleActivityOrder);
                   // 2.更新账户
                   int count = raffleActivityAccountDao.updateAccountQuota(raffleActivityAccount);
                   // 3.创建账户 更新为0,则账户不存在,创建新账户
                   if( 0 == count){
                       raffleActivityAccountDao.insert(raffleActivityAccount);
                   }
                   return 1;
               }catch (DuplicateKeyException e){
                   status.setRollbackOnly();
                   log.error("写入订单记录,唯一索引冲突 userId:{} activityId:{} sku:{}",activityOrderEntity.getUserId(),activityOrderEntity.getActivityId(),activityOrderEntity.getSku());
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
        if(redisService.isExists(cacheKey)) return;
        // 2.缓存活动商品库存
        redisService.setAtomicLong(cacheKey,stockCount);
    }

    @Override
    public boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime) {

        long surplus = redisService.decr(cacheKey);
        if(surplus == 0){
            // 库存消耗完以后,发送MQ消息,更新数据库库存。设置商品库存为0
            eventPublisher.publish(activitySkuStockZeroMessageEvent.topic(),activitySkuStockZeroMessageEvent.buildEventMessage(sku));
        } else if (surplus < 0)  {
            // 库存小于 0 , 恢复为 0 个
            redisService.setAtomicLong(cacheKey,0);
            return false;
        }

        // 1.按照 cacheKey decr 后的值,如 99,98,97 和 key 组成的库存锁的 key 进行使用
        // 2.加锁为了兜底,如果后续有恢复库存,手动处理等【运营是人来操作,会有这种情况发送,系统要做防护】,也不会超卖.因为所有的可用库存key,都被加锁了。
        // 3.设置加锁时间为活动到期 + 延迟1天
        String lockKey = cacheKey + Constants.UNDERLINE+ surplus;
        long expireMills = endDateTime.getTime() - System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
        boolean lock = redisService.setNx(lockKey,expireMills,TimeUnit.MILLISECONDS);
        if(!lock){
            log.info("活动 sku 库存加锁失败 lockKey:{}",lockKey);
        }
        return lock;
    }

    @Override
    public void activitySkuStockConsumerSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        RBlockingQueue<ActivitySkuStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<ActivitySkuStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(activitySkuStockKeyVO,3,TimeUnit.SECONDS);
    }

    @Override
    public ActivitySkuStockKeyVO takeQueueValue() {

        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        RBlockingQueue<ActivitySkuStockKeyVO> destinationQueue  = redisService.getBlockingQueue(cacheKey);
        return destinationQueue .poll();

    }

    @Override
    public void clearQueueValue() {

        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY;
        RBlockingQueue<ActivitySkuStockKeyVO> destinationQueue  = redisService.getBlockingQueue(cacheKey);
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

}

package com.openicu.infrastructure.persistent.repository;

import com.myapp.middleware.db.router.strategy.IDBRouterStrategy;
import com.openicu.domain.activity.model.aggregate.CreateOrderAggregate;
import com.openicu.domain.activity.model.entity.*;
import com.openicu.domain.activity.model.valobj.OrderStateVO;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.infrastructure.persistent.dao.*;
import com.openicu.infrastructure.persistent.po.*;
import com.openicu.infrastructure.persistent.redis.IRedisService;
import com.openicu.types.common.Constants;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import com.openicu.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

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



    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        // 1.从数据库中查询活动库存
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(raffleActivitySku.getStockCountSurplus())
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
                .state(raffleActivity.getState())
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

}

package com.openicu.test.domain.activity;

import com.openicu.domain.activity.model.entity.SkuRechargeEntity;
import com.openicu.domain.activity.model.valobj.OrderTradeTypeVO;
import com.openicu.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.openicu.domain.activity.service.armory.IActivityArmory;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityAccountQuotaServiceTest {

    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;

    @Resource
    private IActivityArmory activityArmory;

    @Test
    public void setUp() {
        log.info("装配活动：{}", activityArmory.assembleActivitySku(9011L));

    }

    @Test
    public void test_createSkuRechargeOrder_duplicate(){

        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("lun");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等性防重使用,同一个业务号2次使用会抛出索引冲突
        skuRechargeEntity.setOutBusinessNo(RandomStringUtils.randomNumeric(11));
        skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.rebate_no_pay_trade);
        String orderId = raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);

        log.info("测试结果:{} ",orderId);

    }

    /**
     * 测试库存消耗和最终一致更新
     * 1. raffle_activity_sku 库表库存可以设置20个
     * 2. 清空 redis 缓存 flushall
     * 3. for 循环20次，消耗完库存，最终数据库剩余库存为0
     */
    @Test
    public void test_createSkuRechargeOrder() throws InterruptedException {

        for(int i = 0 ;  i < 20 ; i++){
            try{
                SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
                skuRechargeEntity.setUserId("zhan");
                skuRechargeEntity.setSku(9011L);
                // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
                skuRechargeEntity.setOutBusinessNo(RandomStringUtils.randomNumeric(12));
                skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.rebate_no_pay_trade);
                String orderId = raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
                log.info("测试结果：{}", orderId);

            }catch (AppException e){
                log.warn(e.getInfo());
            }
        }

        new CountDownLatch(1).await();
    }

    @Test
    public void test_credit_pay_trade(){

        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("kiss");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突
        skuRechargeEntity.setOutBusinessNo(RandomStringUtils.randomNumeric(11));
        skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.credit_pay_trade);
        String orderId = raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
        log.info("测试结果: orderId:{}", orderId);

    }


}

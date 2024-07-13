package com.openicu.test.domain.activity;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.activity.model.entity.ActivityOrderEntity;
import com.openicu.domain.activity.model.entity.ActivityShopCartEntity;
import com.openicu.domain.activity.model.entity.SkuRechargeEntity;
import com.openicu.domain.activity.service.IRaffleOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleOrderTest {

    @Resource
    private IRaffleOrder raffleOrder;

    @Test
    public void test_createSkuRechargeOrder(){

        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("咸鱼12138");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等性防重使用,同一个业务号2次使用会抛出索引冲突
        skuRechargeEntity.setOutBusinessNo("700091009111");
        String orderId = raffleOrder.createRaffleActivityOrder(skuRechargeEntity);

        log.info("测试结果:{} ",orderId);

    }


}

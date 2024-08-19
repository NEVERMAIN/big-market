package com.openicu.test.domain.credit;

import com.openicu.domain.credit.model.entity.TradeEntity;
import com.openicu.domain.credit.model.valobj.TradeNameVO;
import com.openicu.domain.credit.model.valobj.TradeTypeVO;
import com.openicu.domain.credit.service.ICreditAdjustService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditAdjustServiceTest {

    @Resource
    private ICreditAdjustService creditAdjustService;

    @Test
    public void test_createOrder_forward()  {

        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setUserId("xiaofuge");
        tradeEntity.setTradeName(TradeNameVO.REBATE);
        tradeEntity.setTradeType(TradeTypeVO.FORWARD);
        tradeEntity.setAmount(new BigDecimal("10.19"));
        tradeEntity.setOutBusinessNo(RandomStringUtils.randomNumeric(12));
        creditAdjustService.createOrder(tradeEntity);


    }

    @Test
    public void test_createOrder_reverse()  {
        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setUserId("xiaofuge");
        tradeEntity.setTradeName(TradeNameVO.REBATE);
        tradeEntity.setTradeType(TradeTypeVO.REVERSE);
        tradeEntity.setAmount(new BigDecimal("-10.19"));
        tradeEntity.setOutBusinessNo(RandomStringUtils.randomNumeric(12));
        creditAdjustService.createOrder(tradeEntity);

    }

    @Test
    public void test_createOrder_pay() throws InterruptedException {

        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setUserId("chen");
        tradeEntity.setTradeName(TradeNameVO.CONVERT_SKU);
        tradeEntity.setTradeType(TradeTypeVO.REVERSE);
        tradeEntity.setAmount(new BigDecimal("-1.68"));
        tradeEntity.setOutBusinessNo("65104589128");
        creditAdjustService.createOrder(tradeEntity);

        new CountDownLatch(1).await();


    }



}

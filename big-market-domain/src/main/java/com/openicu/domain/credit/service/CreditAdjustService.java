package com.openicu.domain.credit.service;

import com.openicu.domain.credit.model.aggregate.TradeAggregate;
import com.openicu.domain.credit.model.entity.CreditAccountEntity;
import com.openicu.domain.credit.model.entity.CreditOrderEntity;
import com.openicu.domain.credit.model.entity.TradeEntity;
import com.openicu.domain.credit.repository.ICreditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Service
@Slf4j
public class CreditAdjustService implements ICreditAdjustService {

    @Resource
    private ICreditRepository creditRepository;


    @Override
    public String createOrder(TradeEntity tradeEntity) {

        log.info("增加账户积分额度开始 userId:{} tradeName:{} amount:{}", tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getAmount());
        // 1.创建账户积分实体
        CreditAccountEntity creditAccountEntity = TradeAggregate.buildCreditAccountEntity(
                tradeEntity.getUserId(),
                tradeEntity.getAmount()
        );
        // 2. 创建账户订单实体
        CreditOrderEntity creditOrderEntity = TradeAggregate.buildCreditOrderEntity(
                tradeEntity.getUserId(),
                tradeEntity.getTradeName(),
                tradeEntity.getTradeType(),
                tradeEntity.getAmount(),
                tradeEntity.getOutBusinessNo()
        );

        // 3.构建交易聚合对象
        TradeAggregate tradeAggregate = TradeAggregate.builder()
                .userId(tradeEntity.getUserId())
                .creditAccountEntity(creditAccountEntity)
                .creditOrderEntity(creditOrderEntity)
                .build();

        // 4. 保存积分交易订单
        creditRepository.saveUserCreditOrder(tradeAggregate);
        log.info("增加账户积分额度完成 userId:{} orderId:{}", tradeEntity.getUserId(), creditOrderEntity.getOrderId());

        return creditOrderEntity.getOrderId();
    }
}

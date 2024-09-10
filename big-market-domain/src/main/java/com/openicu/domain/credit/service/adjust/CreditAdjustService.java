package com.openicu.domain.credit.service.adjust;

import com.openicu.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.openicu.domain.credit.model.aggregate.TradeAggregate;
import com.openicu.domain.credit.model.entity.CreditAccountEntity;
import com.openicu.domain.credit.model.entity.CreditOrderEntity;
import com.openicu.domain.credit.model.entity.TaskEntity;
import com.openicu.domain.credit.model.entity.TradeEntity;
import com.openicu.domain.credit.model.valobj.TradeTypeVO;
import com.openicu.domain.credit.repository.ICreditRepository;
import com.openicu.domain.credit.service.ICreditAdjustService;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.event.BaseEvent;
import com.openicu.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 积分调额服务【正逆向，增减积分】
 * @author: 云奇迹
 * @date: 2024/8/13
 */
@Service
@Slf4j
public class CreditAdjustService implements ICreditAdjustService {

    @Resource
    private ICreditRepository creditRepository;

    @Resource
    private CreditAdjustSuccessMessageEvent creditAdjustSuccessMessageEvent;


    @Override
    public String createOrder(TradeEntity tradeEntity) {

        log.info("创建账户积分额度订单开始 userId:{} tradeName:{} amount:{}", tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getAmount());
        if (TradeTypeVO.REVERSE.equals(tradeEntity.getTradeType())) {
            CreditAccountEntity creditAccountEntity = creditRepository.queryUserCreditAccount(tradeEntity.getUserId());
            if (null == creditAccountEntity || creditAccountEntity.getAdjustAmount().compareTo(tradeEntity.getAmount()) < 0) {
                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_ACCOUNT.getCode()
                        , ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_ACCOUNT.getInfo());
            }
        }

        // 1.创建账户积分实体
        CreditAccountEntity creditAccountEntity = TradeAggregate.buildCreditAccountEntity(
                tradeEntity.getUserId(),
                tradeEntity.getAmount());

        // 2. 创建账户订单实体
        CreditOrderEntity creditOrderEntity = TradeAggregate.buildCreditOrderEntity(
                tradeEntity.getUserId(),
                tradeEntity.getTradeName(),
                tradeEntity.getTradeType(),
                tradeEntity.getAmount(),
                tradeEntity.getOutBusinessNo());

        // 3. 构建消息任务对象
        CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage creditAdjustSuccessMessage =
                new CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage();
        creditAdjustSuccessMessage.setUserId(tradeEntity.getUserId());
        creditAdjustSuccessMessage.setOrderId(creditOrderEntity.getOrderId());
        creditAdjustSuccessMessage.setAmount(tradeEntity.getAmount());
        creditAdjustSuccessMessage.setOutBusinessNo(tradeEntity.getOutBusinessNo());

        BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> creditAdjustSuccessMessageEventMessage =
                creditAdjustSuccessMessageEvent.buildEventMessage(creditAdjustSuccessMessage);

        TaskEntity taskEntity = TradeAggregate.createTaskEntity(
                tradeEntity.getUserId(),
                creditAdjustSuccessMessageEvent.topic(),
                creditAdjustSuccessMessageEventMessage.getId(),
                creditAdjustSuccessMessageEventMessage
        );

        // 4.构建交易聚合对象
        TradeAggregate tradeAggregate = TradeAggregate.builder()
                .userId(tradeEntity.getUserId())
                .creditAccountEntity(creditAccountEntity)
                .creditOrderEntity(creditOrderEntity)
                .taskEntity(taskEntity)
                .build();

        // 5. 保存积分交易订单
        creditRepository.saveUserCreditTradeOrder(tradeAggregate);
        log.info("创建账户积分额度订单完成 userId:{} orderId:{}", tradeEntity.getUserId(), creditOrderEntity.getOrderId());

        return creditOrderEntity.getOrderId();
    }

    @Override
    public CreditAccountEntity queryUserCreditAccount(String userId) {

        return creditRepository.queryUserCreditAccount(userId);
    }
}

package com.openicu.domain.rebate.service;

import com.openicu.domain.rebate.event.SendRebateMessageEvent;
import com.openicu.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.openicu.domain.rebate.model.entity.BehaviorEntity;
import com.openicu.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.openicu.domain.rebate.model.entity.TaskEntity;
import com.openicu.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import com.openicu.domain.rebate.model.valobj.TaskStateVO;
import com.openicu.domain.rebate.repository.IBehaviorRebateRepository;
import com.openicu.types.common.Constants;
import com.openicu.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
@Service
@Slf4j
public class BehaviorRebateService implements IBehaviorRebateService {

    @Resource
    private IBehaviorRebateRepository behaviorRebateRepository;

    @Resource
    private SendRebateMessageEvent sendRebateMessageEvent;

    @Override
    public List<String> createOrder(BehaviorEntity behaviorEntity) {

        // 1.查询返利配置
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOList =
                behaviorRebateRepository.queryDailyBehaviorRebateConfig(behaviorEntity.getBehaviorTypeVO());

        // 2.构建聚合对象
        List<String> orderIds = new ArrayList<>();
        List<BehaviorRebateAggregate> behaviorRebateAggregateList = new ArrayList<>();
        for (DailyBehaviorRebateVO dailyBehaviorRebateVO : dailyBehaviorRebateVOList) {

            // 拼装业务ID: 用户ID_返利类型_外部透彻业务ID
            String bizId = behaviorEntity.getUserId() + Constants.UNDERLINE + dailyBehaviorRebateVO.getRebateType() + Constants.UNDERLINE + behaviorEntity.getOutBusinessNo();
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(behaviorEntity.getUserId())
                    .orderId(RandomStringUtils.randomNumeric(12))
                    .behaviorType(dailyBehaviorRebateVO.getBehaviorType())
                    .rebateDesc(dailyBehaviorRebateVO.getRebateDesc())
                    .rebateType(dailyBehaviorRebateVO.getBehaviorType())
                    .rebateConfig(dailyBehaviorRebateVO.getRebateConfig())
                    .outBusinessNo(behaviorEntity.getOutBusinessNo())
                    .bizId(bizId)
                    .build();

            orderIds.add(behaviorRebateOrderEntity.getOrderId());

            // .MQ消息
            SendRebateMessageEvent.RebateMessage rebateMessage = SendRebateMessageEvent.RebateMessage.builder()
                    .userId(behaviorEntity.getUserId())
                    .rebateType(dailyBehaviorRebateVO.getRebateType())
                    .rebateConfig(dailyBehaviorRebateVO.getRebateConfig())
                    .rebateDesc(dailyBehaviorRebateVO.getRebateDesc())
                    .bizId(bizId)
                    .build();

            // . 构建事件消息
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> rebateMessageEventMessage =
                    sendRebateMessageEvent.buildEventMessage(rebateMessage);

            // .组装任务对象
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setUserId(behaviorEntity.getUserId());
            taskEntity.setTopic(sendRebateMessageEvent.topic());
            taskEntity.setMessageId(rebateMessageEventMessage.getId());
            taskEntity.setMessage(rebateMessageEventMessage);
            taskEntity.setState(TaskStateVO.create);

            BehaviorRebateAggregate behaviorRebateAggregate = BehaviorRebateAggregate.builder()
                    .userId(behaviorEntity.getUserId())
                    .behaviorRebateOrderEntity(behaviorRebateOrderEntity)
                    .taskEntity(taskEntity)
                    .build();

            behaviorRebateAggregateList.add(behaviorRebateAggregate);

        }

        // 3.存储聚合对象数据
        behaviorRebateRepository.saveUserRebateRecord(behaviorEntity.getUserId(),behaviorRebateAggregateList);

        // 4.返回订单ID集合
        return orderIds;
    }

    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {

        return behaviorRebateRepository.queryOrderByOutBusinessNo(userId,outBusinessNo);

    }
}

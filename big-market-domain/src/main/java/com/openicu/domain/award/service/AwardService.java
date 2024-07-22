package com.openicu.domain.award.service;

import com.openicu.domain.award.event.SendAwardMessageEvent;
import com.openicu.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.openicu.domain.award.model.entity.TaskEntity;
import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import com.openicu.domain.award.model.valobj.TaskStateVO;
import com.openicu.domain.award.reposiotry.IAwardRepository;
import com.openicu.types.event.BaseEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 奖品服务实现
 * @author: 云奇迹
 * @date: 2024/7/19
 */
@Service
public class AwardService implements IAwardService{

    @Resource
    private IAwardRepository awardRepository;

    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;

    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {

        // 1.构建消息对象
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessage.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessage.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessage.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        sendAwardMessage.setOrderId(userAwardRecordEntity.getOrderId());

        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage = sendAwardMessageEvent.buildEventMessage(sendAwardMessage);

        // 2.构建任务对象
        TaskEntity taskEntity =  new TaskEntity();
        taskEntity.setUserId(userAwardRecordEntity.getUserId());
        taskEntity.setTopic(sendAwardMessageEvent.topic());
        taskEntity.setMessageId(sendAwardMessageEventMessage.getId());
        taskEntity.setMessage(sendAwardMessageEventMessage);
        taskEntity.setState(TaskStateVO.create);

        // 3.构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .taskEntity(taskEntity)
                .userAwardRecordEntity(userAwardRecordEntity)
                .build();

        // 4.存储聚合对象 - 一个事务下,用户的中奖记录
        awardRepository.saveUserAwardRecord(userAwardRecordAggregate);

    }
}

package com.openicu.domain.award.model.aggregate;

import com.openicu.domain.award.model.entity.TaskEntity;
import com.openicu.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAwardRecordAggregate {

    /** 任务实体对象 */
    private TaskEntity taskEntity;
    /** 用户抽奖奖品实体对象 */
    private UserAwardRecordEntity userAwardRecordEntity;

}

package com.openicu.domain.rebate.repository;

import com.openicu.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.openicu.domain.rebate.model.valobj.BehaviorTypeVO;
import com.openicu.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/7/29
 */
public interface IBehaviorRebateRepository {

    /**
     * 根据行为类型查询每日行为返利配置。
     *
     * @param behaviorTypeVO 行为类型的VO对象，包含具体的类型信息。
     * @return 返回每日行为返利的VO列表，每个VO详细描述了一种行为的返利配置。
     */
    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    /**
     * 保存用户行为返利记录。
     *
     * @param userId 用户ID，用于标识返利记录所属的用户。
     * @param behaviorRebateAggregateList 行为返利汇总列表，包含用户的具体行为及其返利信息。
     */
    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregateList);
}

package com.openicu.domain.strategy.model.entity;

import com.openicu.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyEntity {

    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 抽奖规则的模型 */
    private String ruleModels;

    public String[] ruleModels(){
        if(StringUtils.isBlank(ruleModels)) {
            return null;
        }
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight(){
        String[] ruleModels = this.ruleModels();
        if(ruleModels != null){
            for (String ruleModel : ruleModels) {
                if("rule_weight".equals(ruleModel)) {
                    return ruleModel;
                }
            }
        }
        return null;
    }


}

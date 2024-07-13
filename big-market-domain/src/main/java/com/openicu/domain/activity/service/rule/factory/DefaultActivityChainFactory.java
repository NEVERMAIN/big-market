package com.openicu.domain.activity.service.rule.factory;

import com.openicu.domain.activity.service.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description: 责任链工厂
 * @author: 云奇迹
 * @date: 2024/7/9
 */
@Service
public class DefaultActivityChainFactory {

    private final IActionChain actionChain;

    public DefaultActivityChainFactory(Map<String,IActionChain> actionChainGroup){
        actionChain = actionChainGroup.get(ActionModel.ACTIVITY_BASE_ACTION.getCode());
        actionChain.appendNext(actionChainGroup.get(ActionModel.ACTIVITY_SKU_STOCK_ACTION.getCode()));
        actionChain.next().appendNext(actionChainGroup.get(ActionModel.ACTIVITY_COUNT_ACTION.getCode()));
    }

    public IActionChain openActionChain(){
        return this.actionChain;
    }


    @Getter
    @AllArgsConstructor
    public enum ActionModel{

        ACTIVITY_BASE_ACTION("activity_base_action","活动的库存,时间校验"),
        ACTIVITY_SKU_STOCK_ACTION("activity_sku_action_chain","活动sku库存"),
        ACTIVITY_COUNT_ACTION("activity_count_chain","活动参与次数"),

        ;

        private final String code;
        private final String info;
    }



}

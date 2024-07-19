package com.openicu.domain.activity.service.quota.rule.factory;

import com.openicu.domain.activity.service.quota.rule.IActionChain;
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
    }

    public IActionChain openActionChain(){
        return this.actionChain;
    }


    @Getter
    @AllArgsConstructor
    public enum ActionModel{

        ACTIVITY_BASE_ACTION("activity_base_action","活动的库存,时间校验"),
        ACTIVITY_SKU_STOCK_ACTION("activity_sku_stock_action","活动sku库存"),

        ;

        private final String code;
        private final String info;
    }



}

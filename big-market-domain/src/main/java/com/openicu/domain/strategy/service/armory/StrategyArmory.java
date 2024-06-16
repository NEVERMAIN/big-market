package com.openicu.domain.strategy.service.armory;

import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;
import com.openicu.domain.strategy.resposity.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 策略装配库(兵工厂), 负责初始化策略计算
 * @author: 云奇迹
 * @date: 2024/6/15
 */
@Service
@Slf4j
public class StrategyArmory implements IStrategyArmory {

    @Resource
    private IStrategyRepository repository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {

        // 1. 查询策略配置
        List<StrategyAwardEntity> strategyAwardEntityList = repository.queryStrategyAwardList(strategyId);

        // 2.获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntityList.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 3. 用 1 % 0.0001 获取概率范围 百分位,千分位,万分位
        BigDecimal rateRange = BigDecimal.valueOf(convert(minAwardRate.doubleValue()));

        // 4. 生成策略奖品概率查找表「这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高」
        List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntityList) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            // 计算出每个概率值需要存放到查找表的数量,循环填充
            for (int i = 0; i < awardRate.multiply(rateRange).setScale(0, RoundingMode.CEILING).intValue(); i++) {
               strategyAwardSearchRateTables.add(awardId);
            }
        }

        // 5. 对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchRateTables);

        // 6. 生成出 Map 集合,key 值 , 对应的就是后续的概率值,通过概率来获得对应的奖品ID
        Map<Integer,Integer> shuffleStrategyAwardSearchRateTables = new LinkedHashMap<>();
        for(int i = 0 ; i < strategyAwardSearchRateTables.size() ; i ++){
            shuffleStrategyAwardSearchRateTables.put(i,strategyAwardSearchRateTables.get(i));
        }

        // 7.存放到 Redis
        repository.storeStrategyAwardSearchRateTable(strategyId,shuffleStrategyAwardSearchRateTables.size(),shuffleStrategyAwardSearchRateTables);

        return true;

    }

    /**
     * 转换计算: 只根据小数位来计算,如[0.1返回100],[0.009返回1000],[0.0018返回10000]
     */
    private double convert(double min){
        double current = min;
        double max = 1;
        while(current < 1){
            current = current * 10;
            max = max * 10;
        }
        return max;
    }



    @Override
    public Integer getRandomAwardId(Long strategyId) {

        // 1.分布式部署下,不一定为当前应用做的策略装配,也就是值不一定会保存到本应用,而是分布式应用,所以需要从 Redis 中获取
        int rateRange = repository.getRateRange(strategyId);
        // 2.通过生成的随机值,获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(strategyId,new SecureRandom().nextInt(rateRange));
    }
}

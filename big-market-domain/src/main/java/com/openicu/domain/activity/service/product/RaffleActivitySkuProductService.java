package com.openicu.domain.activity.service.product;

import com.openicu.domain.activity.model.entity.SkuProductEntity;
import com.openicu.domain.activity.repository.IActivityRepository;
import com.openicu.domain.activity.service.IRaffleActivitySkuProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/21
 */
@Service
@Slf4j
public class RaffleActivitySkuProductService implements IRaffleActivitySkuProductService {

    @Resource
    private IActivityRepository activityRepository;

    @Override
    public List<SkuProductEntity> querySkuProductEntityByActivityId(Long activityId) {
        return activityRepository.querySkuProductEntityListByActivityId(activityId);
    }
}

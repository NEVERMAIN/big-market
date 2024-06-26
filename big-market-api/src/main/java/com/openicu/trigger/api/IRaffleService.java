package com.openicu.trigger.api;


import com.openicu.trigger.api.dto.RaffleAwardListRequestDTO;
import com.openicu.trigger.api.dto.RaffleAwardListResponseDTO;
import com.openicu.trigger.api.dto.RaffleRequestDTO;
import com.openicu.trigger.api.dto.RaffleResponseDTO;
import com.openicu.types.model.Response;

import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/26
 */
public interface IRaffleService {

    /**
     * 策略装配接口
     * @param strategyId 策略ID
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表配置
     *
     * @param requestDTO 抽奖奖品列表查询请求参数
     * @return 奖品列表数据
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);

    /**
     * 随机抽奖接口
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);




}

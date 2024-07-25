package com.openicu.trigger.http;

import com.alibaba.fastjson.JSON;
import com.openicu.domain.strategy.model.entity.RaffleAwardEntity;
import com.openicu.domain.strategy.model.entity.RaffleFactorEntity;
import com.openicu.domain.strategy.model.entity.StrategyAwardEntity;
import com.openicu.domain.strategy.service.IRaffleAward;
import com.openicu.domain.strategy.service.IRaffleStrategy;
import com.openicu.domain.strategy.service.armory.IStrategyArmory;
import com.openicu.trigger.api.IRaffleStrategyService;
import com.openicu.trigger.api.dto.RaffleAwardListRequestDTO;
import com.openicu.trigger.api.dto.RaffleAwardListResponseDTO;
import com.openicu.trigger.api.dto.RaffleStrategyRequestDTO;
import com.openicu.trigger.api.dto.RaffleStrategyResponseDTO;
import com.openicu.types.enums.ResponseCode;
import com.openicu.types.exception.AppException;
import com.openicu.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/6/26
 */
@Slf4j
@RestController
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/strategy")
public class RaffleStrategyController implements IRaffleStrategyService {

    @Resource
    private IRaffleAward raffleAward;

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private IStrategyArmory strategyArmory;

    /**
     * 策略装配，将策略信息装配到缓存中
     * @param strategyId 策略ID
     * @return 装配结果
     */
    @RequestMapping(value = "strategy_armory",method = RequestMethod.GET)
    @Override
    public Response<Boolean> strategyArmory(Long strategyId) {
        try{
            log.info("抽奖策略装配开始 strategyId:{} ",strategyId);
            boolean armoryStatus  = strategyArmory.assembleLotteryStrategy(strategyId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(armoryStatus)
                    .build();

            log.info("抽奖策略装配完成 strategyId:{} response:{}",strategyId,response);

            return response;
        }catch (Exception e){
            log.error("抽奖策略装配失败 strategyId:{}", strategyId,e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 查询奖品列表
     * @param requestDTO 抽奖奖品列表查询请求参数
     * @return 奖品列表
     */
    @RequestMapping(value = "query_raffle_award_list",method = RequestMethod.POST)
    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(@RequestBody RaffleAwardListRequestDTO requestDTO) {

        try{
            log.info("查询抽奖奖品列表配置开始 strategyId:{}",requestDTO.getStrategyId());

            // 1.查询奖品配置
            List<StrategyAwardEntity> strategyAwardEntities  = raffleAward.queryRaffleStrategyAwardList(requestDTO.getStrategyId());
            List<RaffleAwardListResponseDTO> raffleAwardListResponseDTOList = new ArrayList<>(strategyAwardEntities.size());
            for(StrategyAwardEntity strategyAward : strategyAwardEntities){
                RaffleAwardListResponseDTO raffleAwardListResponseDTO = RaffleAwardListResponseDTO.builder()
                                .awardId(strategyAward.getAwardId())
                                .awardTitle(strategyAward.getAwardTitle())
                                .awardSubtitle(strategyAward.getAwardSubtitle())
                                .sort(strategyAward.getSort())
                                .build();
                raffleAwardListResponseDTOList.add(raffleAwardListResponseDTO);
            }

            Response<List<RaffleAwardListResponseDTO>> response = Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleAwardListResponseDTOList)
                    .build();

            log.info("查询抽奖奖品列表配置完成 strategyId：{} response: {}", requestDTO.getStrategyId(), JSON.toJSONString(response));

            return response;

        }catch (Exception e){
            log.error("查询抽奖奖品列表配置失败 strategyId：{}", requestDTO.getStrategyId(), e);
            return Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 随机抽奖接口
     * @param requestDTO 请求参数 {"strategyId":1000001}
     * @return 抽奖结果
     */
    @RequestMapping(value = "random_raffle", method = RequestMethod.POST)
    @Override
    public Response<RaffleStrategyResponseDTO> randomRaffle(@RequestBody RaffleStrategyRequestDTO requestDTO) {

        try {
            log.info("随机抽奖 strategyId:{}",requestDTO.getStrategyId());
            // 1. 调用抽奖接口
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(RaffleFactorEntity.builder()
                    .userId("system")
                    .strategyId(requestDTO.getStrategyId())
                    .build());

            // 2. 封装返回结果
            Response<RaffleStrategyResponseDTO> response = Response.<RaffleStrategyResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(RaffleStrategyResponseDTO.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();

            log.info("随机抽奖完成 strategyId: {} response: {}", requestDTO.getStrategyId(), JSON.toJSONString(response));

            return response;
        } catch (AppException e) {
            log.error("随机抽奖失败 strategyId：{} {}", requestDTO.getStrategyId(), e.getInfo());
            return Response.<RaffleStrategyResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        }catch (Exception e){
            log.error("随机抽奖失败 strategyId：{}", requestDTO.getStrategyId(), e);
            return Response.<RaffleStrategyResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}

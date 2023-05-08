package com.glowworm.football.booking.web.webapi.stadium.service;

import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.stadium.*;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.stadium.query.QueryStadium;
import com.glowworm.football.booking.domain.stadium.vo.*;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-04-10 11:50
 */
@Slf4j
@Service
public class StadiumWebService {

    @Autowired
    private IStadiumService stadiumService;
    @Autowired
    private IStadiumScheduleService scheduleService;

    public List<StadiumVo> queryList (WxContext ctx, QueryStadium query) {


        List<StadiumBean> stadiumBeans = stadiumService.queryList(query);

        return stadiumBeans.stream().map(item -> {

            // 球场下的所有场地
            List<StadiumBlockBean> blockList = item.getBlockList();

            // 草皮、人数规格
            List<String> swardTypeList = blockList.stream().map(block -> block.getSwardType().getDesc()).distinct().collect(Collectors.toList());
            List<String> scaleTypeList = blockList.stream().map(block -> block.getScaleType().getAbbr()).distinct().collect(Collectors.toList());

            return StadiumVo.builder()
                    .id(item.getId())
                    .stadiumName(item.getStadiumName())
                    .address(item.getAddress())
                    .district(item.getDistrict())
                    .stadiumStatus(item.getStadiumStatus().getCode())
                    .mainImageUrl(item.getMainImageUrl())
                    .swardTypeList(swardTypeList)
                    .scaleTypeList(scaleTypeList)
                    .build();
        }).collect(Collectors.toList());
    }

    public StadiumInfoVo getDetail (WxContext ctx, Long id) {

        return stadiumService.getDetail(id);
    }

    public List<ScheduleVo> queryScheduleList (QuerySchedule query) {

        List<FtStadiumSchedulePo> schedule = scheduleService.querySchedule(query);

        if (CollectionUtils.isEmpty(schedule)) {
            return Collections.emptyList();
        }

        return schedule.stream().map(item -> ScheduleVo.builder()
                .id(item.getId())
                .stadiumId(item.getStadiumId())
                .blockId(item.getBlockId())
                .date(item.getDate())
                .clockBegin(item.getClockBegin().getDesc())
                .clockEnd(item.getClockEnd().getDesc())
                .isAfternoon(item.getClockBegin().getIsAfternoon().getCode())
                .weekName(DateUtils.getWeekName(item.getDate()))
                .isWeekend(DateUtils.isWeekend(item.getDate()))
                .status(item.getStatus().getCode())
                .price(item.getPrice())
                .build())
                .collect(Collectors.toList());
    }

    public CombineScheduleVo getCombineScheduleVo (QuerySchedule query) {

        FtStadiumPo stadium = stadiumService.getStadium(query.getStadiumId());
        FtStadiumBlockPo block = stadiumService.getBlock(query.getBlockId());
        StadiumScheduleBean schedule = scheduleService.getSchedule(query.getId());

        return CombineScheduleVo.builder()
                .stadium(StadiumVo.builder()
                        .id(stadium.getId())
                        .stadiumName(stadium.getStadiumName())
                        .address(stadium.getAddress())
                        .build())
                .block(StadiumBlockVo.builder()
                        .id(block.getId())
                        .blockName(block.getBlockName())
                        .scaleTypeDesc(block.getScaleType().getDesc())
                        .swardTypeDesc(block.getSwardType().getDesc())
                        .build())
                .schedule(ScheduleVo.builder()
                        .id(schedule.getId())
                        .date(schedule.getDate())
                        .clockBegin(schedule.getClockBegin().getDesc())
                        .clockEnd(schedule.getClockEnd().getDesc())
                        .isAfternoon(schedule.getClockBegin().getIsAfternoon().getCode())
                        .build())
                .build();
    }
}

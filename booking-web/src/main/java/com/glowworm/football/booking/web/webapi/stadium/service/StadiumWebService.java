package com.glowworm.football.booking.web.webapi.stadium.service;

import com.glowworm.football.booking.dao.po.stadium.*;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.stadium.*;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.stadium.query.QueryStadium;
import com.glowworm.football.booking.domain.stadium.vo.*;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.stadium.IStadiumCollectService;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.stadium.IStadiumTagService;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private IStadiumCollectService stadiumCollectService;
    @Autowired
    private IStadiumTagService stadiumTagService;

    public List<StadiumVo> queryList (UserBean user, QueryStadium query) {

        List<StadiumBean> stadiumBeans = stadiumService.queryList(query);
        if (CollectionUtils.isEmpty(stadiumBeans)) {
            return Collections.emptyList();
        }
        List<Long> stadiumIds = stadiumBeans.stream().map(StadiumBean::getId).collect(Collectors.toList());

        // 收藏数据
        List<FtStadiumCollectPo> collectList = stadiumCollectService.queryStadiumCollect(user);
        List<Long> collectStadiumIds = collectList.stream()
                .filter(item -> Utils.isPositive(item.getCollectStatus().getCode()))
                .map(FtStadiumCollectPo::getStadiumId)
                .collect(Collectors.toList());

        // 球场tag数据
        Map<Long, List<FtStadiumTagPo>> tagMap = stadiumTagService.queryTagMap(stadiumIds);


        return stadiumBeans.stream().map(item -> {

            // 球场下的所有场地
            List<StadiumBlockBean> blockList = item.getBlockList();

            // 草皮、人数规格
            List<String> swardTypeList = blockList.stream().map(block -> block.getSwardType().getDesc()).distinct().collect(Collectors.toList());
            List<String> scaleTypeList = blockList.stream().map(block -> block.getScaleType().getAbbr()).distinct().collect(Collectors.toList());

            // 是否收藏
            Integer isCollect = TrueFalse.getByBoolean(collectStadiumIds.contains(item.getId())).getCode();

            // 球场标签
            List<FtStadiumTagPo> tags = tagMap.getOrDefault(item.getId(), Collections.emptyList());
            List<String> tagStringList = tags.stream().map(FtStadiumTagPo::getTag).collect(Collectors.toList());

            return StadiumVo.builder()
                    .id(item.getId())
                    .stadiumName(item.getStadiumName())
                    .address(item.getAddress())
                    .introduce(item.getIntroduce())
                    .district(item.getDistrict())
                    .stadiumStatus(item.getStadiumStatus().getCode())
                    .mainImageUrl(item.getMainImageUrl())
                    .swardTypeList(swardTypeList)
                    .scaleTypeList(scaleTypeList)
                    .isCollect(isCollect)
                    .tags(tagStringList)
                    .lat(item.getLat())
                    .lon(item.getLon())
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

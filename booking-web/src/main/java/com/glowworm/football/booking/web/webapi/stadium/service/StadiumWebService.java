package com.glowworm.football.booking.web.webapi.stadium.service;

import com.glowworm.football.booking.dao.po.matching.FtMatchingPo;
import com.glowworm.football.booking.dao.po.stadium.*;
import com.glowworm.football.booking.domain.booking.enums.BookingType;
import com.glowworm.football.booking.domain.booking.query.QueryBooking;
import com.glowworm.football.booking.domain.booking.vo.BookingVo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.matching.enums.MatchingStatus;
import com.glowworm.football.booking.domain.matching.query.QueryMatching;
import com.glowworm.football.booking.domain.matching.vo.MatchingVo;
import com.glowworm.football.booking.domain.stadium.*;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.stadium.query.QueryStadium;
import com.glowworm.football.booking.domain.stadium.vo.*;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.booking.IBookingService;
import com.glowworm.football.booking.service.matching.IMatchingService;
import com.glowworm.football.booking.service.stadium.IStadiumCollectService;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.stadium.IStadiumTagService;
import com.glowworm.football.booking.service.team.ITeamService;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.service.util.Utils;
import com.glowworm.football.booking.web.webapi.booking.service.BookingWebService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
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
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private BookingWebService bookingWebService;
    @Autowired
    private IMatchingService matchingService;

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
                    .averagePrice(item.getAveragePrice())
                    .distance(item.getDistance())
                    .build();
        }).collect(Collectors.toList());
    }

    public StadiumInfoVo getDetail (WxContext ctx, Long id) {

        return stadiumService.getDetail(id);
    }

    public List<ScheduleVo> queryScheduleList (UserBean user, QuerySchedule query) {

        List<FtStadiumSchedulePo> schedule = scheduleService.querySchedule(query);

        if (CollectionUtils.isEmpty(schedule)) {
            return Collections.emptyList();
        }

        return enhanceSchedule(user, schedule);
    }

    public ScheduleVo getScheduleDetail (UserBean user, Long scheduleId) {

        FtStadiumSchedulePo schedule = scheduleService.getSchedule(scheduleId);
        if (Objects.isNull(schedule)) {
            return ScheduleVo.builder().build();
        }

        return enhanceSchedule(user, schedule);
    }

    private ScheduleVo enhanceSchedule (UserBean user, FtStadiumSchedulePo schedule) {

        // stadium
        FtStadiumPo stadium = stadiumService.getStadium(schedule.getStadiumId());

        // block
        FtStadiumBlockPo block = stadiumService.getBlock(schedule.getBlockId());

        // booking
        List<BookingVo> bookings = bookingService.query(QueryBooking.builder().scheduleId(schedule.getId()).build());
        bookings = bookingWebService.enhanceTeamSimpleInfo(user, bookings);
        boolean isWhole = bookings.stream().map(BookingVo::getBookingType).anyMatch(BookingType.WHOLE::equals);

        // team
        List<Long> teamIds = bookings.stream().map(BookingVo::getTeamId).collect(Collectors.toList());
        List<TeamSimpleVo> teams = teamService.queryRandomTeamList(teamIds);
        teams = tryFillDefaultTeams(teams);

        // matching
        List<MatchingVo> matching = matchingService.queryMatchingVo(QueryMatching.builder()
                .scheduleId(schedule.getId())
                .matchingStatus(MatchingStatus.MATCHING)
                .build());
        boolean hasMatching = matching.stream().map(MatchingVo::getUserId).anyMatch(user.getId()::equals);

        List<MatchingVo> matched = matchingService.queryMatchingVo(QueryMatching.builder()
                .scheduleId(schedule.getId())
                .matchingStatus(MatchingStatus.MATCHED)
                .build());

        return ScheduleVo.builder()
                .id(schedule.getId())
                .stadiumId(schedule.getStadiumId())
                .stadiumName(stadium.getStadiumName())
                .blockId(schedule.getBlockId())
                .blockName(block.getBlockName())
                .scaleType(block.getScaleType())
                .date(schedule.getDate())
                .clockBegin(schedule.getClockBegin().getDesc())
                .clockEnd(schedule.getClockEnd().getDesc())
                .isAfternoon(schedule.getClockBegin().getIsAfternoon().getCode())
                .weekName(DateUtils.getWeekName(schedule.getDate()))
                .isWeekend(DateUtils.isWeekend(schedule.getDate()))
                .status(schedule.getStatus())
                .price(schedule.getPrice())
                .teams(teams)
                .matching(matching)
                .matched(matched)
                .matchingCount(matching.size())
                .bookings(bookings)
                .hasMatching(TrueFalse.getByBoolean(hasMatching).getCode())
                .isWholeBooking(TrueFalse.getByBoolean(isWhole).getCode())
                .build();
    }

    private List<TeamSimpleVo> tryFillDefaultTeams (List<TeamSimpleVo> teams) {

        TeamSimpleVo defaultTeam = teamService.getDefaultTeam();

        if (CollectionUtils.isEmpty(teams)) {
            return Lists.newArrayList(defaultTeam, defaultTeam);
        }

        if (teams.size() == 1) {
            teams.add(defaultTeam);
            return teams;
        }

        return teams;
    }

    private List<ScheduleVo> enhanceSchedule (UserBean user, List<FtStadiumSchedulePo> schedule) {

        // 查询block信息
        List<Long> blockIds = schedule.stream().map(FtStadiumSchedulePo::getBlockId).distinct().collect(Collectors.toList());
        List<FtStadiumBlockPo> blocks = stadiumService.queryBlock(blockIds);
        Map<Long, FtStadiumBlockPo> blockMap = blocks.stream().collect(Collectors.toMap(FtStadiumBlockPo::getId, Function.identity()));

        // 查询booking信息
        List<Long> scheduleIds = schedule.stream().map(FtStadiumSchedulePo::getId).collect(Collectors.toList());
        List<BookingVo> bookings = bookingService.query(QueryBooking.builder()
                .scheduleIds(scheduleIds)
                .build());
        Map<Long, List<BookingVo>> scheduleId2Bookings = bookings.stream().collect(Collectors.groupingBy(BookingVo::getScheduleId));

        // 查询team信息
        List<TeamSimpleVo> allTeams = teamService.getRandomTeams();
        Map<Long, TeamSimpleVo> teamMap = allTeams.stream().collect(Collectors.toMap(TeamSimpleVo::getId, Function.identity()));

        // 查询matching信息
        Map<Long, List<FtMatchingPo>> schedule2MatchingList = matchingService.queryMatching(QueryMatching.builder()
                .scheduleIds(scheduleIds)
                .matchingStatus(MatchingStatus.MATCHING)
                .build());

        List<ScheduleVo> result = schedule.stream().map(item -> ScheduleVo.builder()
                        .id(item.getId())
                        .stadiumId(item.getStadiumId())
                        .blockId(item.getBlockId())
                        .date(item.getDate())
                        .clockBegin(item.getClockBegin().getDesc())
                        .clockEnd(item.getClockEnd().getDesc())
                        .isAfternoon(item.getClockBegin().getIsAfternoon().getCode())
                        .weekName(DateUtils.getWeekName(item.getDate()))
                        .isWeekend(DateUtils.isWeekend(item.getDate()))
                        .status(item.getStatus())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        // enhance
        result.forEach(item -> {

            // block信息
            FtStadiumBlockPo block = blockMap.get(item.getBlockId());
            item.setBlockName(block.getBlockName());
            item.setScaleType(block.getScaleType());

            // matching信息
            List<FtMatchingPo> matching = schedule2MatchingList.getOrDefault(item.getId(), Collections.emptyList());
            item.setMatchingCount(matching.size());

            // 当前用户matching状态
            boolean hasMatching = matching.stream().map(FtMatchingPo::getUserId).anyMatch(user.getId()::equals);
            item.setHasMatching(TrueFalse.getByBoolean(hasMatching).getCode());

            // team信息
            List<BookingVo> bookingList = scheduleId2Bookings.getOrDefault(item.getId(), Collections.emptyList());
            if (CollectionUtils.isEmpty(bookingList)) {
                return;
            }
            List<Long> teamIds = bookingList.stream().map(BookingVo::getTeamId).collect(Collectors.toList());
            List<TeamSimpleVo> teams = teamIds.stream()
                    .map(tid -> teamMap.getOrDefault(tid, TeamSimpleVo.builder().build()))
                    .collect(Collectors.toList());
            item.setTeams(teams);

            // bookingType信息
            boolean isWhole = bookingList.stream().map(BookingVo::getBookingType).anyMatch(BookingType.WHOLE::equals);
            item.setIsWholeBooking(TrueFalse.getByBoolean(isWhole).getCode());

        });

        return result;
    }

    public CombineScheduleVo getCombineScheduleVo (QuerySchedule query) {

        FtStadiumPo stadium = stadiumService.getStadium(query.getStadiumId());
        FtStadiumBlockPo block = stadiumService.getBlock(query.getBlockId());
        FtStadiumSchedulePo schedule = scheduleService.getSchedule(query.getId());

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

package com.glowworm.football.booking.web.webapi.booking.service;

import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.booking.vo.BookingVo;
import com.glowworm.football.booking.domain.booking.vo.MyBookingVo;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.team.ITeamService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-04 20:07
 */
@Slf4j
@Service
public class BookingWebService {

    @Autowired
    private ITeamService teamService;
    @Autowired
    private IStadiumService stadiumService;
    @Autowired
    private IStadiumScheduleService scheduleService;

    public List<MyBookingVo> convertMyBookingVo (List<BookingVo> bookingList) {

        if (CollectionUtils.isEmpty(bookingList)) {
            return Collections.emptyList();
        }

        // stadium
        List<Long> stadiumIds = bookingList.stream().map(BookingVo::getStadiumId).distinct().collect(Collectors.toList());
        List<FtStadiumPo> stadiums = stadiumService.queryStadium(stadiumIds);
        Map<Long, FtStadiumPo> stadiumMap = stadiums.stream().collect(Collectors.toMap(FtStadiumPo::getId, Function.identity()));

        // block
        List<Long> blockIds = bookingList.stream().map(BookingVo::getBlockId).distinct().collect(Collectors.toList());
        List<FtStadiumBlockPo> blocks = stadiumService.queryBlock(blockIds);
        Map<Long, FtStadiumBlockPo> blockMap = blocks.stream().collect(Collectors.toMap(FtStadiumBlockPo::getId, Function.identity()));

        // schedule
        List<Long> scheduleIds = bookingList.stream().map(BookingVo::getScheduleId).distinct().collect(Collectors.toList());
        List<FtStadiumSchedulePo> schedules = scheduleService.querySchedule(QuerySchedule.builder()
                .ids(scheduleIds)
                .build());
        Map<Long, FtStadiumSchedulePo> scheduleMap = schedules.stream().collect(Collectors.toMap(FtStadiumSchedulePo::getId, Function.identity()));

        // team
        List<TeamSimpleVo> allTeams = teamService.getRandomTeams();
        Map<Long, TeamSimpleVo> teamMap = allTeams.stream().collect(Collectors.toMap(TeamSimpleVo::getId, Function.identity()));

        // convert
        return bookingList.stream().map(item -> {

            FtStadiumPo stadium = stadiumMap.getOrDefault(item.getStadiumId(), FtStadiumPo.builder().build());
            FtStadiumBlockPo block = blockMap.getOrDefault(item.getBlockId(), FtStadiumBlockPo.builder().build());
            FtStadiumSchedulePo schedule = scheduleMap.getOrDefault(item.getScheduleId(), FtStadiumSchedulePo.builder().build());
            TeamSimpleVo team = teamMap.getOrDefault(item.getTeamId(), TeamSimpleVo.builder().build());

            return MyBookingVo.builder()
                    .id(item.getId())
                    .stadiumId(item.getStadiumId())
                    .stadiumName(stadium.getStadiumName())
                    .blockId(item.getBlockId())
                    .blockName(block.getBlockName())
                    .scheduleId(item.getScheduleId())
                    .date(schedule.getDate())
                    .clockBegin(schedule.getClockBegin().getDesc())
                    .clockEnd(schedule.getClockEnd().getDesc())
                    .userId(item.getUserId())
                    .teamId(item.getTeamId())
                    .teamSimpleVo(team)
                    .bookingType(item.getBookingType())
                    .bookingStatus(item.getBookingStatus())
                    .price(item.getPrice())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<BookingVo> enhanceTeamSimpleInfo (UserBean userBean, List<BookingVo> bookingList) {

        if (CollectionUtils.isEmpty(bookingList)) {
            return Collections.emptyList();
        }

        List<Long> teamIds = bookingList.stream().map(BookingVo::getTeamId)
                .distinct()
                .collect(Collectors.toList());

        // 查随机队伍信息
        List<Long> randomTeamIds = teamIds.stream().filter(id -> !Utils.isPositive(id)).collect(Collectors.toList());
        Map<Long, TeamSimpleVo> randomTeamMap = teamService.queryRandomTeam(randomTeamIds);

        // 查真实队伍信息

        // enhance随机队伍信息
        bookingList.forEach(item -> item.setTeamSimpleVo(randomTeamMap.get(item.getTeamId())));

        // enhanceBelonging
        enhanceBelonging(userBean, bookingList);

        return bookingList;
    }

    private void enhanceBelonging (UserBean userBean, List<BookingVo> bookingList) {

        if (CollectionUtils.isEmpty(bookingList)) {
            return;
        }

        bookingList.forEach(item -> {
            // 判断是否为预订人
            boolean isBookingUser = item.getUserId().equals(userBean.getId());
            if (isBookingUser) {
                item.setIsBelong(TrueFalse.TRUE.getCode());
            }
        });

    }
}

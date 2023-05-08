package com.glowworm.football.booking.service.team.impl;

import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.booking.query.QueryBooking;
import com.glowworm.football.booking.domain.booking.vo.BookingVo;
import com.glowworm.football.booking.domain.stadium.StadiumScheduleBean;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.service.booking.IBookingService;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.team.ITeamActionService;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-04 16:29
 */
@Service
@Slf4j
public class TeamActionServiceImpl implements ITeamActionService {

    @Autowired
    private IStadiumScheduleService scheduleService;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private TeamConfig teamConfig;


    @Override
    public Long randomTeamId(Long scheduleId) {

        /**
         * 1. 先根据scheduleId查出block & date的所有scheduleId
         * 2. 再查这一组scheduleId的booking信息
         * 3. 再从randomTeam中刨去booking中的teamId
         * 4. 从剩余randomTeam中随机一个
         */

        // 查block & date的这一批schedule
        StadiumScheduleBean schedule = scheduleService.getSchedule(scheduleId);
        List<FtStadiumSchedulePo> scheduleInGroup = scheduleService.querySchedule(QuerySchedule.builder()
                .blockId(schedule.getBlockId())
                .dateBegin(DateUtils.getTimestamp2String(schedule.getDate()))
                .dateEnd(DateUtils.getTimestamp2String(schedule.getDate()))
                .build());

        List<Long> scheduleIds = scheduleInGroup.stream()
                .map(FtStadiumSchedulePo::getId)
                .collect(Collectors.toList());

        // 查这批schedule下的booking
        List<BookingVo> bookings = bookingService.query(QueryBooking.builder()
                .scheduleIds(scheduleIds)
                .build());

        List<Long> usedRandomTeamIds = bookings.stream().map(BookingVo::getTeamId)
                .filter(id -> !Utils.isPositive(id))
                .distinct()
                .collect(Collectors.toList());

        // 随机球队配置
        List<TeamSimpleVo> randomTeam = teamConfig.getRandomTeam();

        // 刨去已经出现了的teamId
        List<TeamSimpleVo> restRandomTeam = randomTeam.stream()
                .filter(item -> !usedRandomTeamIds.contains(item.getId()))
                .collect(Collectors.toList());

        return randomTeamId(restRandomTeam);
    }

    private Long randomTeamId (List<TeamSimpleVo> randomTeam) {

        if (CollectionUtils.isEmpty(randomTeam)) {
            return 0L;
        }

        if (randomTeam.size() == 1) {
            return randomTeam.get(0).getId();
        }

        // 随机一个
        Random random = new Random();
        int index = random.nextInt(randomTeam.size() - 1);
        return randomTeam.get(index).getId();
    }
}

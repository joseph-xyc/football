package com.glowworm.football.booking.service.booking.impl;

import com.glowworm.football.booking.dao.mapper.FtBookingMapper;
import com.glowworm.football.booking.dao.mapper.FtStadiumScheduleMapper;
import com.glowworm.football.booking.dao.po.booking.FtBookingPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.booking.enums.BookingMatchingStatus;
import com.glowworm.football.booking.domain.booking.enums.BookingStatus;
import com.glowworm.football.booking.domain.booking.enums.BookingType;
import com.glowworm.football.booking.domain.booking.vo.BookingFormVo;
import com.glowworm.football.booking.domain.stadium.StadiumScheduleBean;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.booking.IBookingActionService;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-17 18:10
 */
@Slf4j
@Service
public class BookingActionServiceImpl implements IBookingActionService {

    @Autowired
    private FtBookingMapper bookingMapper;
    @Autowired
    private FtStadiumScheduleMapper scheduleMapper;
    @Autowired
    private IStadiumScheduleService scheduleService;

    @Override
    public void booking(UserBean user, BookingFormVo bookingVo) {

        StadiumScheduleBean schedule = scheduleService.getSchedule(bookingVo.getScheduleId());

        validSchedule(schedule);

        doBooking(user, schedule, bookingVo);

    }

    private void doBooking (UserBean user, StadiumScheduleBean schedule, BookingFormVo bookingVo) {

        FtBookingPo bookingPo = FtBookingPo.builder()
                .stadiumId(schedule.getStadiumId())
                .blockId(schedule.getBlockId())
                .scheduleId(schedule.getId())
                .userId(user.getId())
                .teamId(bookingVo.getTeamId())
                .expectSignUpNum(bookingVo.getExpectSignUpNum())
                .expectRecruitNum(bookingVo.getExpectRecruitNum())
                .bookingType(BookingType.getByCode(bookingVo.getBookingType()))
                .bookingStatus(BookingStatus.PLANNING)
                .matchingStatus(Utils.isPositive(bookingVo.getExpectRecruitNum(), BookingMatchingStatus.MATCHING, BookingMatchingStatus.NO_MATCHING))
                .build();
        bookingMapper.insert(bookingPo);


        FtStadiumSchedulePo schedulePo = FtStadiumSchedulePo.builder()
                .id(schedule.getId())
                .status(ScheduleStatus.PLANNING)
                .build();
        scheduleMapper.updateById(schedulePo);
    }

    private void validSchedule (StadiumScheduleBean schedule) {

        Utils.isTrue(Objects.nonNull(schedule), "不存在的时刻");
        Utils.isTrue(schedule.getStatus().equals(ScheduleStatus.ENABLE), "当前时刻不可用，请选择其他时间");

    }
}

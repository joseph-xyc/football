package com.glowworm.football.booking.service.booking.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtBookingMapper;
import com.glowworm.football.booking.dao.mapper.FtStadiumScheduleMapper;
import com.glowworm.football.booking.dao.po.booking.FtBookingPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.booking.enums.BookingStatus;
import com.glowworm.football.booking.domain.booking.enums.BookingType;
import com.glowworm.football.booking.domain.booking.vo.BookingFormVo;
import com.glowworm.football.booking.domain.booking.vo.CarFormInBookingVo;
import com.glowworm.football.booking.domain.car.vo.LaunchCarFormVo;
import com.glowworm.football.booking.domain.stadium.StadiumScheduleBean;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.booking.IBookingActionService;
import com.glowworm.football.booking.service.car.ICarActionService;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.team.ITeamActionService;
import com.glowworm.football.booking.service.util.Utils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ICarActionService carActionService;
    @Autowired
    private ITeamActionService teamActionService;

    @Transactional
    @Override
    public Long booking(UserBean user, BookingFormVo bookingVo) {

        StadiumScheduleBean schedule = scheduleService.getSchedule(bookingVo.getScheduleId());

        validSchedule(schedule, bookingVo);

        return doBooking(user, schedule, bookingVo);
    }

    @Transactional
    @Override
    public void cancel(UserBean user, Long id) {

        /**
         * 1. 检查订单状态
         * 2. 检查此人是否是预订人
         * 3. 更新booking状态
         */

        Utils.isTrue(Objects.nonNull(id), "预订id不能为空");

        FtBookingPo bookingPo = bookingMapper.selectById(id);

        boolean validBookingStatus = Lists.newArrayList(BookingStatus.WAIT_STADIUM_CONFIRM, BookingStatus.BOOKED)
                .contains(bookingPo.getBookingStatus());

        Utils.isTrue(validBookingStatus, "预订状态非法, 无法取消预订");
        Utils.isTrue(bookingPo.getUserId().equals(user.getId()), "您无权操作其他人的预订单");

        bookingMapper.updateById(FtBookingPo.builder()
                .id(id)
                .bookingStatus(BookingStatus.PERSONAL_CANCEL)
                .build());

        //TODO 此处关于booking影响schedule的相关操作，可能需要重新抽象和抽离，这里先暂时定制的by case实现

        ScheduleStatus scheduleStatus = ScheduleStatus.ENABLE;

        // 半预订, 检查预订条数
        if (bookingPo.getBookingType().equals(BookingType.HALF)) {
            Long halfBookedCount = bookingMapper.selectCount(Wrappers.lambdaQuery(FtBookingPo.class)
                    .eq(FtBookingPo::getScheduleId, bookingPo.getScheduleId())
                    .eq(FtBookingPo::getBookingType, BookingType.HALF)
                    .eq(FtBookingPo::getBookingStatus, BookingStatus.BOOKED));

            scheduleStatus = halfBookedCount > 0 ? ScheduleStatus.HALF_BOOKED : ScheduleStatus.ENABLE;
        }

        // 更新schedule的状态
        FtStadiumSchedulePo schedulePo = FtStadiumSchedulePo.builder()
                .id(bookingPo.getScheduleId())
                .status(scheduleStatus)
                .build();
        scheduleMapper.updateById(schedulePo);
    }

    private Long doBooking (UserBean user, StadiumScheduleBean schedule, BookingFormVo bookingVo) {

        // 尝试生成随机的teamId
        enhanceTeamId(bookingVo);

        // save车
        Long carId = saveCar(user, schedule, bookingVo);
        bookingVo.setCarId(carId);

        // save Booking
        return saveBooking(user, schedule, bookingVo);
    }

    private void enhanceTeamId (BookingFormVo bookingVo) {

        // 如果已存在真实teamId, 则跳过
        if (Utils.isPositive(bookingVo.getTeamId())) {
            return;
        }

        // 随机生成teamId
        Long randomTeamId = teamActionService.randomTeamId(bookingVo.getScheduleId());
        bookingVo.setTeamId(randomTeamId);
    }

    private Long saveBooking (UserBean user, StadiumScheduleBean schedule, BookingFormVo bookingVo) {

        FtBookingPo bookingPo = FtBookingPo.builder()
                .stadiumId(schedule.getStadiumId())
                .blockId(schedule.getBlockId())
                .scheduleId(schedule.getId())
                .userId(user.getId())
                .teamId(bookingVo.getTeamId())
                .carId(bookingVo.getCarId())
                .bookingType(BookingType.getByCode(bookingVo.getBookingType()))
                .bookingStatus(BookingStatus.BOOKED)
                .build();
        bookingMapper.insert(bookingPo);

        // 更新schedule
        updateSchedule(bookingPo);

        return bookingPo.getId();
    }

    private void updateSchedule (FtBookingPo booking) {

        ScheduleStatus scheduleStatus = ScheduleStatus.BOOKED;

        // BookingType = HALF时, 判断是否存在半场booked记录，来更新schedule的状态
        if (booking.getBookingType().equals(BookingType.HALF)) {
            Long halfBookedCount = bookingMapper.selectCount(Wrappers.lambdaQuery(FtBookingPo.class)
                    .eq(FtBookingPo::getScheduleId, booking.getScheduleId())
                    .eq(FtBookingPo::getBookingType, BookingType.HALF)
                    .eq(FtBookingPo::getBookingStatus, BookingStatus.BOOKED));

            scheduleStatus = halfBookedCount >= 2 ? ScheduleStatus.BOOKED : ScheduleStatus.HALF_BOOKED;
        }

        // 更新schedule的状态
        FtStadiumSchedulePo schedulePo = FtStadiumSchedulePo.builder()
                .id(booking.getScheduleId())
                .status(scheduleStatus)
                .build();
        scheduleMapper.updateById(schedulePo);
    }

    private Long saveCar (UserBean user, StadiumScheduleBean schedule, BookingFormVo bookingVo) {

        // 有ID, 直接用ID
        if (Utils.isPositive(bookingVo.getCarId())) {
            return bookingVo.getCarId();
        }

        // 有表单信息, 直接根据表单创建
        if (Objects.nonNull(bookingVo.getCarFormVo())) {

            CarFormInBookingVo carFormVo = bookingVo.getCarFormVo();

            return carActionService.launch(user, LaunchCarFormVo.builder()
                    .stadiumId(schedule.getStadiumId())
                    .blockId(schedule.getBlockId())
                    .scheduleId(schedule.getId())
                    .teamId(bookingVo.getTeamId())
                    .carName(carFormVo.getCarName())
                    .carTopic(carFormVo.getCarTopic())
                    .recruitNum(carFormVo.getRecruitNum())
                    .carType(BookingType.getByCode(bookingVo.getBookingType()).getCarType().getCode())
                    .build());
        }

        // 啥都没有, 返回0
        return 0L;
    }

    private void validSchedule (StadiumScheduleBean schedule, BookingFormVo bookingVo) {

        Utils.isTrue(Objects.nonNull(schedule), "不存在的时刻");
        Utils.throwError(schedule.getStatus().equals(ScheduleStatus.BOOKED), "当前时刻已被预订啦~ 下次请早");
        Utils.throwError(schedule.getStatus().equals(ScheduleStatus.DISABLE), "当前时刻不可用, 无法预订");
        // schedule处在半预订态，但表单是全场预订
        Utils.throwError(schedule.getStatus().equals(ScheduleStatus.HALF_BOOKED)
                && bookingVo.getBookingType().equals(BookingType.WHOLE.getCode()), "当前时刻已被包了半场啦~ 无法全预定啦");

    }
}

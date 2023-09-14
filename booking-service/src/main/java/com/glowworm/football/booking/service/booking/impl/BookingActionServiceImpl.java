package com.glowworm.football.booking.service.booking.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtBookingMapper;
import com.glowworm.football.booking.dao.mapper.FtStadiumScheduleMapper;
import com.glowworm.football.booking.dao.po.booking.FtBookingPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.booking.enums.BookingStatus;
import com.glowworm.football.booking.domain.booking.enums.BookingType;
import com.glowworm.football.booking.domain.booking.vo.BookingFormVo;
import com.glowworm.football.booking.domain.booking.vo.CarFormInBookingVo;
import com.glowworm.football.booking.domain.car.vo.LaunchCarFormVo;
import com.glowworm.football.booking.domain.msg.MsgBean;
import com.glowworm.football.booking.domain.msg.enums.MsgBizType;
import com.glowworm.football.booking.domain.msg.enums.MsgSysType;
import com.glowworm.football.booking.domain.stadium.StadiumScheduleBean;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.booking.IBookingActionService;
import com.glowworm.football.booking.service.car.ICarActionService;
import com.glowworm.football.booking.service.msg.IMsgActionService;
import com.glowworm.football.booking.service.msg.impl.MsgConfig;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.team.ITeamActionService;
import com.glowworm.football.booking.service.util.DateUtils;
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
    private ITeamActionService teamActionService;
    @Autowired
    private MsgConfig msgConfig;
    @Autowired
    private IStadiumService stadiumService;
    @Autowired
    private IMsgActionService msgActionService;

    @Transactional
    @Override
    public Long booking(UserBean user, BookingFormVo bookingVo) {

        FtStadiumSchedulePo schedule = scheduleService.getSchedule(bookingVo.getScheduleId());

        validSchedule(schedule, bookingVo);

        return doBooking(user, schedule, bookingVo);
    }

    @Transactional
    @Override
    public void confirm(UserBean user, Long id) {

        Utils.isTrue(user.getUserType().isAdmin(), "只有客服人员才能确认预订信息, 请您使用客服账号");
        Utils.isTrue(Objects.nonNull(id), "预订id不能为空");

        FtBookingPo booking = bookingMapper.selectById(id);
        Utils.isTrue(booking.getBookingStatus().equals(BookingStatus.WAIT_STADIUM_CONFIRM), "只有[待球场确认]状态的订单才可以进行此操作");

        // 校验schedule是否已占用
        Long scheduleId = booking.getScheduleId();

        BookingType bookingType = booking.getBookingType();

        // 当前已确认的预订情况
        Long halfBookedCount = halfBookedCount(scheduleId);
        Long wholeBookedCount = wholeBookedCount(scheduleId);

        // 全场订单校验
        if (bookingType.equals(BookingType.WHOLE)) {
            Utils.throwError(halfBookedCount > 0, "此场地已存在半场订单, 无法再确认全场订单了");
            Utils.throwError(wholeBookedCount == 1, "此场地已存在全场订单, 无法再确认全场订单了");
        }

        // 半场订单校验
        if (bookingType.equals(BookingType.HALF)) {
            Utils.throwError(halfBookedCount >= 2, "此场地已确认预订了2个半场, 无法再确认半场订单了");
            Utils.throwError(wholeBookedCount == 1, "此场地已存在全场订单, 无法再确认半场订单了");
        }

        // 更新booking状态
        bookingMapper.updateById(FtBookingPo.builder()
                .id(id)
                .bookingStatus(BookingStatus.BOOKED)
                .build());

        // 更新schedule的状态
        ScheduleStatus scheduleStatus = ScheduleStatus.HALF_BOOKED;
        if (bookingType.equals(BookingType.WHOLE)) {
            scheduleStatus = ScheduleStatus.BOOKED;
        }
        if (bookingType.equals(BookingType.HALF)) {
            scheduleStatus = halfBookedCount == 1 ? ScheduleStatus.BOOKED : ScheduleStatus.HALF_BOOKED;
        }

        FtStadiumSchedulePo schedulePo = FtStadiumSchedulePo.builder()
                .id(booking.getScheduleId())
                .status(scheduleStatus)
                .build();
        scheduleMapper.updateById(schedulePo);

        // 发送消息
        sendConfirmMsg(user, booking);
    }

    private void sendConfirmMsg (UserBean user, FtBookingPo booking) {

        // stadium
        FtStadiumPo stadium = stadiumService.getStadium(booking.getStadiumId());
        // block
        FtStadiumBlockPo block = stadiumService.getBlock(booking.getBlockId());
        // schedule
        FtStadiumSchedulePo schedule = scheduleService.getSchedule(booking.getScheduleId());

        String telStr = stadium.getContactPhone();
        String blockStr = String.format("%s %s", stadium.getStadiumName(), block.getBlockName());
        String timeStr = String.format("%s %s", DateUtils.getTimestamp2String(schedule.getDate()), schedule.getClockBegin().getDesc() + " ~ " + schedule.getClockEnd().getDesc());

        String content = String.format(msgConfig.renderBookingConfirmTemp(blockStr, telStr, timeStr));

        msgActionService.newMsg(MsgBean.builder()
                .userId(user.getId())
                .msgSysType(MsgSysType.NORMAL)
                .msgBizType(MsgBizType.ORDER)
                .date(DateUtils.getNow())
                .content(content)
                .build());
    }

    private Long halfBookedCount (Long scheduleId) {

        return bookingMapper.selectCount(Wrappers.lambdaQuery(FtBookingPo.class)
                .eq(FtBookingPo::getScheduleId, scheduleId)
                .eq(FtBookingPo::getBookingType, BookingType.HALF)
                .eq(FtBookingPo::getBookingStatus, BookingStatus.BOOKED));
    }

    private Long wholeBookedCount (Long scheduleId) {

        return bookingMapper.selectCount(Wrappers.lambdaQuery(FtBookingPo.class)
                .eq(FtBookingPo::getScheduleId, scheduleId)
                .eq(FtBookingPo::getBookingType, BookingType.WHOLE)
                .eq(FtBookingPo::getBookingStatus, BookingStatus.BOOKED));
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

        cancelMsg(user, bookingPo);
    }

    private void cancelMsg (UserBean user, FtBookingPo booking) {

        // stadium
        FtStadiumPo stadium = stadiumService.getStadium(booking.getStadiumId());
        // block
        FtStadiumBlockPo block = stadiumService.getBlock(booking.getBlockId());
        // schedule
        FtStadiumSchedulePo schedule = scheduleService.getSchedule(booking.getScheduleId());

        Long bookingIdStr = booking.getId();
        String blockStr = String.format("%s %s", stadium.getStadiumName(), block.getBlockName());
        String timeStr = String.format("%s %s", DateUtils.getTimestamp2String(schedule.getDate()), schedule.getClockBegin().getDesc() + " ~ " + schedule.getClockEnd().getDesc());

        String content = String.format(msgConfig.getBookingCancelTemp(), bookingIdStr, blockStr, timeStr);

        msgActionService.newMsg(MsgBean.builder()
                .userId(user.getId())
                .msgSysType(MsgSysType.NORMAL)
                .msgBizType(MsgBizType.ORDER)
                .date(DateUtils.getNow())
                .content(content)
                .build());
    }

    private Long doBooking (UserBean user, FtStadiumSchedulePo schedule, BookingFormVo bookingVo) {

        // 尝试生成随机的teamId
        enhanceTeamId(bookingVo);

        // save Booking
        Long bookingId = saveBooking(user, schedule, bookingVo);

        // 发消息
        doBookingMsg(schedule, user);

        return bookingId;
    }

    private void doBookingMsg (FtStadiumSchedulePo schedule, UserBean user) {

        // stadium
        FtStadiumPo stadium = stadiumService.getStadium(schedule.getStadiumId());
        // block
        FtStadiumBlockPo block = stadiumService.getBlock(schedule.getBlockId());

        String contactStr = stadium.getContactPhone();
        String blockStr = msgConfig.renderBlockStr(stadium.getStadiumName(), block.getBlockName());
        String timeStr = msgConfig.renderTimeStr(schedule.getDate(), schedule.getClockBegin(), schedule.getClockEnd());
        String content = msgConfig.renderDoBookingTemp(blockStr, contactStr, timeStr);

        msgActionService.newMsg(MsgBean.builder()
                .userId(user.getId())
                .msgSysType(MsgSysType.NORMAL)
                .msgBizType(MsgBizType.ORDER)
                .date(DateUtils.getNow())
                .content(content)
                .build());
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

    private Long saveBooking (UserBean user, FtStadiumSchedulePo schedule, BookingFormVo bookingVo) {

        FtBookingPo bookingPo = FtBookingPo.builder()
                .stadiumId(schedule.getStadiumId())
                .blockId(schedule.getBlockId())
                .scheduleId(schedule.getId())
                .userId(user.getId())
                .teamId(bookingVo.getTeamId())
                .bookingType(bookingVo.getBookingType())
                .bookingStatus(BookingStatus.WAIT_STADIUM_CONFIRM)
                .price(calcPrice(bookingVo.getBookingType(), schedule))
                .build();
        bookingMapper.insert(bookingPo);

        return bookingPo.getId();
    }

    private Integer calcPrice (BookingType bookingType, FtStadiumSchedulePo schedule) {

        if (bookingType.equals(BookingType.HALF)) {
            return schedule.getPrice() / 2;
        }

        return schedule.getPrice();
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

//    private Long saveCar (UserBean user, FtStadiumSchedulePo schedule, BookingFormVo bookingVo) {
//
//        // 有ID, 直接用ID
//        if (Utils.isPositive(bookingVo.getCarId())) {
//            return bookingVo.getCarId();
//        }
//
//        // 有表单信息, 直接根据表单创建
//        if (Objects.nonNull(bookingVo.getCarFormVo())) {
//
//            CarFormInBookingVo carFormVo = bookingVo.getCarFormVo();
//
//            return carActionService.launch(user, LaunchCarFormVo.builder()
//                    .stadiumId(schedule.getStadiumId())
//                    .blockId(schedule.getBlockId())
//                    .scheduleId(schedule.getId())
//                    .teamId(bookingVo.getTeamId())
//                    .carName(carFormVo.getCarName())
//                    .carTopic(carFormVo.getCarTopic())
//                    .recruitNum(carFormVo.getRecruitNum())
//                    .carType(BookingType.getByCode(bookingVo.getBookingType()).getCarType().getCode())
//                    .build());
//        }
//
//        // 啥都没有, 返回0
//        return 0L;
//    }

    private void validSchedule (FtStadiumSchedulePo schedule, BookingFormVo bookingVo) {

        Utils.isTrue(Objects.nonNull(schedule), "不存在的时刻");
        Utils.throwError(schedule.getStatus().equals(ScheduleStatus.BOOKED), "当前时刻已被预订啦~ 下次请早");
        Utils.throwError(schedule.getStatus().equals(ScheduleStatus.DISABLE), "当前时刻不可用, 无法预订");
        // schedule处在半预订态，但表单是全场预订
        Utils.throwError(schedule.getStatus().equals(ScheduleStatus.HALF_BOOKED)
                && bookingVo.getBookingType().equals(BookingType.WHOLE), "当前时刻已被包了半场啦~ 无法全预定啦");

    }
}

package com.glowworm.football.booking.web.webapi.booking.service;

import com.glowworm.football.booking.dao.po.passenger.FtPassengerPo;
import com.glowworm.football.booking.domain.booking.vo.BookingVo;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.passenger.IPassengerService;
import com.glowworm.football.booking.service.team.ITeamService;
import com.glowworm.football.booking.service.util.Utils;
import com.google.common.collect.Lists;
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
 * @date 2023-05-04 20:07
 */
@Slf4j
@Service
public class BookingWebService {

    @Autowired
    private ITeamService teamService;
    @Autowired
    private IPassengerService passengerService;

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

        // 查询订单所关联的车
        List<Long> carIds = bookingList.stream().map(BookingVo::getCarId).filter(Utils::isPositive).distinct().collect(Collectors.toList());
        Map<Long, List<FtPassengerPo>> carId2PassengersMap = passengerService.queryPassengerOnBoard(carIds);

        bookingList.forEach(item -> {

            // 判断是否为预订人
            boolean isBookingUser = item.getUserId().equals(userBean.getId());
            if (isBookingUser) {
                item.setIsBelong(TrueFalse.TRUE.getCode());
                return;
            }

            // 判断是否为乘客
            List<FtPassengerPo> passengers = carId2PassengersMap.getOrDefault(item.getCarId(), Collections.emptyList());
            boolean isBelong = passengers.stream().anyMatch(p -> p.getPassengerId().equals(userBean.getId()));
            item.setIsBelong(TrueFalse.getByBoolean(isBelong).getCode());

        });

    }
}

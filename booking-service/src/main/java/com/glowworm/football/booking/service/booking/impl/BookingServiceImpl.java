package com.glowworm.football.booking.service.booking.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtBookingMapper;
import com.glowworm.football.booking.dao.po.booking.FtBookingPo;
import com.glowworm.football.booking.domain.booking.query.QueryBooking;
import com.glowworm.football.booking.domain.booking.vo.BookingVo;
import com.glowworm.football.booking.service.booking.IBookingService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-28 12:00
 */
@Slf4j
@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private FtBookingMapper bookingMapper;

    @Override
    public List<BookingVo> query(QueryBooking query) {

        List<FtBookingPo> bookingPos = bookingMapper.selectList(Wrappers.lambdaQuery(FtBookingPo.class)
                .eq(Objects.nonNull(query.getScheduleId()), FtBookingPo::getScheduleId, query.getScheduleId())
                .in(!CollectionUtils.isEmpty(query.getScheduleIds()), FtBookingPo::getScheduleId, query.getScheduleIds())
                .eq(Objects.nonNull(query.getBookingStatus()), FtBookingPo::getBookingStatus, query.getBookingStatus()));

        if (CollectionUtils.isEmpty(bookingPos)) {
            return Collections.emptyList();
        }

        return Utils.copy(bookingPos, BookingVo.class);
    }
}

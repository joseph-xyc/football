package com.glowworm.football.booking.service.booking;

import com.glowworm.football.booking.domain.booking.query.QueryBooking;
import com.glowworm.football.booking.domain.booking.vo.BookingVo;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-28 11:58
 * 预订相关查询服务
 */
public interface IBookingService {

    List<BookingVo> query (QueryBooking query);
}

package com.glowworm.football.booking.service.booking;

import com.glowworm.football.booking.domain.booking.vo.BookingFormVo;
import com.glowworm.football.booking.domain.user.UserBean;

/**
 * @author xuyongchang
 * @date 2023-04-17 17:29
 * 预约action接口
 */
public interface IBookingActionService {

    void booking (UserBean user, BookingFormVo bookingVo);
}

package com.glowworm.football.booking.domain.booking.query;

import com.glowworm.football.booking.domain.booking.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-28 11:59
 */
@Data
@Builder
public class QueryBooking {

    private Long scheduleId;

    private List<Long> scheduleIds;

    private BookingStatus bookingStatus;
}

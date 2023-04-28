package com.glowworm.football.booking.domain.booking.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-28 11:57
 * 预订信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingVo {

    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Long scheduleId;

    private Long userId;

    private Long teamId;

    private Long carId;

    private Integer bookingType;

    private Integer bookingStatus;
}

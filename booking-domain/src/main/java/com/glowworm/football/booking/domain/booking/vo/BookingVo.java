package com.glowworm.football.booking.domain.booking.vo;

import com.glowworm.football.booking.domain.booking.enums.BookingStatus;
import com.glowworm.football.booking.domain.booking.enums.BookingType;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
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

    private TeamSimpleVo teamSimpleVo;

    private BookingType bookingType;

    private BookingStatus bookingStatus;

    private Integer price;

    private Integer isBelong;
}

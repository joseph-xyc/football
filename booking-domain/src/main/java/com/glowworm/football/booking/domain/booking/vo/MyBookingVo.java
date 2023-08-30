package com.glowworm.football.booking.domain.booking.vo;

import com.glowworm.football.booking.domain.booking.enums.BookingStatus;
import com.glowworm.football.booking.domain.booking.enums.BookingType;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-08-29 17:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyBookingVo {

    private Long id;

    private Long stadiumId;

    private String stadiumName;

    private Long blockId;

    private String blockName;

    private Long scheduleId;

    private Timestamp date;

    private String clockBegin;

    private String clockEnd;

    private Long userId;

    private Long teamId;

    private TeamSimpleVo teamSimpleVo;

    private BookingType bookingType;

    private BookingStatus bookingStatus;

    private Integer price;

}

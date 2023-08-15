package com.glowworm.football.booking.domain.booking.vo;

import com.glowworm.football.booking.domain.booking.enums.BookingType;
import com.glowworm.football.booking.domain.car.vo.LaunchCarFormVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-17 17:31
 * 预约表单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingFormVo {

    // 时刻ID
    private Long scheduleId;

    // 球队ID
    private Long teamId;

    // 预约类型
    private BookingType bookingType;


}

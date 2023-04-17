package com.glowworm.football.booking.domain.booking.vo;

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

    // 预期报名人数
    private Integer expectSignUpNum;

    // 预期招募人数
    private Integer expectRecruitNum;

    // 预约类型
    private Integer bookingType;


}

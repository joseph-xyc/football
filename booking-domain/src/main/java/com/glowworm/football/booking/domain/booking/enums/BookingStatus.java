package com.glowworm.football.booking.domain.booking.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-17 16:54
 * 预约状态
 */
@Getter
@AllArgsConstructor
public enum BookingStatus {

    WAIT_STADIUM_CONFIRM(0, "待球场确认"),

    BOOKED(10, "预订成功"),

    PERSONAL_CANCEL(50, "个人取消"),

    STADIUM_CANCEL(51, "球场撤销"),

    SYS_CANCEL(59, "系统撤销"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

}

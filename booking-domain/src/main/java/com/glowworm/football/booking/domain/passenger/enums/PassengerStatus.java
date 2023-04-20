package com.glowworm.football.booking.domain.passenger.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-20 19:35
 */
@Getter
@AllArgsConstructor
public enum PassengerStatus {

    TO_BE_CONFIRM(0, "待确认"),

    GET_ON(1, "已上车"),

    GET_OFF(2, "已下车"),

    INVALID(20, "无效"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

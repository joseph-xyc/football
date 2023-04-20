package com.glowworm.football.booking.domain.car.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-20 16:31
 * 车状态
 */
@Getter
@AllArgsConstructor
public enum CarStatus {

    WAITING(0, "载客中"),

    FULL(1, "满载"),

    OVERLOAD(2, "超载"),

    ORDERED(10, "成单"),

    CANCEL(20, "取消"),

    EXPIRE(21, "过期"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

}

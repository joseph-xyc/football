package com.glowworm.football.booking.domain.booking.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.glowworm.football.booking.domain.car.enums.CarType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author xuyongchang
 * @date 2023-04-17 16:50
 * 预约类型
 */
@Getter
@AllArgsConstructor
public enum BookingType {

    WHOLE(0, "全场", CarType.TRUCK),

    HALF(1, "半场", CarType.CAR),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    private final CarType carType;

    public static BookingType getByCode (Integer code) {

        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(WHOLE);
    }
}

package com.glowworm.football.booking.domain.booking.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BookingType {

    HALF(1, "半场"),

    WHOLE(2, "全场"),


    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    public static BookingType getByCode (Integer code) {

        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(WHOLE);
    }
}

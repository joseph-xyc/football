package com.glowworm.football.booking.domain.booking.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
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

    WHOLE(0, "全场", "内训"),

    HALF(1, "半场", "约队PK"),

    WHEEL(2, "车轮战", "3队及以上"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    private final String brief;

    public static BookingType getByCode (Integer code) {

        return Arrays.stream(values()).filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
    }
}

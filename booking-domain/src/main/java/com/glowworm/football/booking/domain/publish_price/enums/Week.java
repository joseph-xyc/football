package com.glowworm.football.booking.domain.publish_price.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author xuyongchang
 * @date 2023-05-08 11:45
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Week {

    MONDAY(1, "周一"),

    TUESDAY(2, "周二"),

    WEDNESDAY(3, "周三"),

    THURSDAY(4, "周四"),

    FRIDAY(5, "周五"),

    SATURDAY(6, "周六"),

    SUNDAY(7, "周日"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    public static Week getByCode (Integer code) {

        return Arrays.stream(values()).filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
    }
}

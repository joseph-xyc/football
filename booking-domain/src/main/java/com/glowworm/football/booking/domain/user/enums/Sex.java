package com.glowworm.football.booking.domain.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-05-09 16:55
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sex {

    MALE(1, "男"),

    FEMALE(2, "女")

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

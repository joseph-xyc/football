package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-10 11:54
 */
@Getter
@AllArgsConstructor
public enum ScaleType {

    C_5(0, "5人场", "5"),

    C_7(1, "7人场", "7"),

    C_8(3, "8人场", "8"),

    C_11(4, "11人场", "11"),

    X_LARGE(5, "大操场", "综"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    private final String abbr;
}

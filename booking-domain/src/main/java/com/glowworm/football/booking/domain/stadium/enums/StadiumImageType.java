package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-10 21:26
 */
@Getter
@AllArgsConstructor
public enum StadiumImageType {

    MAIN(0, "首图"),

    COMMON(1, "普通图"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

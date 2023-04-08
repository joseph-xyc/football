package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:00
 * 球场状态
 */
@Getter
@AllArgsConstructor
public enum StadiumStatus {

    DISABLE(0, "不可用"),

    ENABLE(1, "可用"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    public static StadiumStatus get (Integer code) {

        if (Objects.isNull(code)) {
            return null;
        }

        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }


}

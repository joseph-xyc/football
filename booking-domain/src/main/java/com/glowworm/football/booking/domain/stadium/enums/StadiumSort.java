package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-06-06 20:12
 * 球场排序条件
 */
@Getter
@AllArgsConstructor
public enum StadiumSort {

    DISTANCE_FIRST(0, "距离优先"),

    LOW_PRICE_FIRST(1, "低价优先"),

    HIGH_PRICE_FIRST(2, "高价优先"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    public static StadiumSort get (Integer code) {

        if (Objects.isNull(code)) {
            return null;
        }

        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}

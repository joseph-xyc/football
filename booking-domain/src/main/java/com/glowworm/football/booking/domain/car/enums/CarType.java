package com.glowworm.football.booking.domain.car.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author xuyongchang
 * @date 2023-04-20 16:31
 * 车型
 */
@Getter
@AllArgsConstructor
public enum CarType {

    TRUCK(1, "卡车", "全场"),

    CAR(2, "轿车", "半场"),

    TRICYCLE(3, "三轮车", "车轮战"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    private final String brief;

    public static CarType getByCode (Integer code) {

        return Arrays.stream(values()).filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
    }
}

package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:03
 * 球场-场地-时刻
 */
@Getter
@AllArgsConstructor
public enum ScheduleClock {

    T_8(8, "08:00", TrueFalse.FALSE),

    T_10(10, "10:00", TrueFalse.FALSE),

    T_12(12, "12:00", TrueFalse.FALSE),

    T_14(14, "14:00", TrueFalse.TRUE),

    T_16(16, "16:00", TrueFalse.TRUE),

    T_18(18, "18:00", TrueFalse.TRUE),

    T_20(20, "20:00", TrueFalse.TRUE),

    T_22(22, "22:00", TrueFalse.TRUE),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    private final TrueFalse isAfternoon;

    public static List<Integer> getAfternoonClock () {

        return Arrays.stream(values())
                .filter(item -> item.getIsAfternoon().getCode() > 0)
                .map(ScheduleClock::getCode)
                .collect(Collectors.toList());
    }
}

package com.glowworm.football.booking.domain.passenger.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-20 19:30
 */
@Getter
@AllArgsConstructor
public enum BoardingWay {

    SELF(0, "主动上车"),

    INVITE(1, "邀请上车"),

    MATCHING(2, "匹配上车"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    public static BoardingWay getByCode (Integer code) {

        if (Objects.isNull(code)) {
            return BoardingWay.SELF;
        }
        return Arrays.stream(values()).filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
    }
}

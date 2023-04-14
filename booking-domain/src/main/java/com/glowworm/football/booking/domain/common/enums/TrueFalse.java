package com.glowworm.football.booking.domain.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:11
 */
@Getter
@AllArgsConstructor
public enum TrueFalse {

    TRUE(1, "是"),

    FALSE(0, "否"),

    ;
    private final Integer code;

    private final String desc;

    public static TrueFalse getByBoolean (Boolean tf) {

        if (tf) {
            return TrueFalse.TRUE;
        } else {
            return TrueFalse.FALSE;
        }
    }
}

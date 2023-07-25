package com.glowworm.football.booking.domain.matching.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-07-20 20:41
 */
@Getter
@AllArgsConstructor
public enum MatchingStatus {

    MATCHING(0, "匹配中"),

    MATCHED(1, "已匹配"),

    CANCEL(9, "取消匹配"),

    END(10, "已结束"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

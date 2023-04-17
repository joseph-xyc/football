package com.glowworm.football.booking.domain.booking.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-17 17:11
 * 预约表-匹配状态
 */
@Getter
@AllArgsConstructor
public enum BookingMatchingStatus {

    NO_MATCHING(0, "跳过匹配"),

    MATCHING(1, "匹配中"),

    DONE(10, "匹配完成"),

    CANCEL(50, "取消匹配"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

}

package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:15
 * 球场-场地状态
 */
@Getter
@AllArgsConstructor
public enum StadiumBlockStatus {

    DISABLE(0, "不可用"),

    ENABLE(1, "可用"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

}

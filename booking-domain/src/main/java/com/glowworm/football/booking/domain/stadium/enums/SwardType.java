package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-10 11:54
 * 草皮类型
 */
@Getter
@AllArgsConstructor
public enum SwardType {

    ARTIFICIAL(0, "人工草"),

    NATURAL(1, "天然草"),

    PLASTIC(3, "塑胶"),

    OTHER(9, "其他"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

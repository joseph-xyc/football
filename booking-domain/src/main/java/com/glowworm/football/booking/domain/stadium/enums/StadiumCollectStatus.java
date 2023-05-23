package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-05-23 14:55
 * 用户&球场收藏状态
 */
@Getter
@AllArgsConstructor
public enum StadiumCollectStatus {

    INVALID(0, "无效"),

    VALID(1, "有效"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

package com.glowworm.football.booking.domain.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-05-09 16:43
 * 用户等级
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserLevel {

    BALL_BOY(0, "球童"),

    BRIDE(1, "新秀"),

    STARTING(2, "主力"),

    CORE(3, "核心"),

    KING(4, "球王"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

package com.glowworm.football.booking.domain.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-17 17:46
 * 用户类型
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {

    VISITOR(0, "访客"),

    ORDINARY(1, "普通用户"),

    STADIUM_ADMIN(10, "球场客服"),

    SYS(20, "系统管理员"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

}

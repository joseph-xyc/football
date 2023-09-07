package com.glowworm.football.booking.domain.msg.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author xuyongchang
 * @date 2023-09-06 17:28
 * 消息系统类型
 */
@Getter
@AllArgsConstructor
public enum MsgSysType {

    NORMAL(0, "普通消息"),

    SYS(1, "系统公告"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    public static MsgSysType getByCode (Integer code) {

        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(NORMAL);
    }
}

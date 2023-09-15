package com.glowworm.football.booking.domain.msg.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author xuyongchang
 * @date 2023-09-06 18:08
 * 消息业务类型
 */
@Getter
@AllArgsConstructor
public enum MsgBizType {

    UNKNOWN(0, "未知"),

    ORDER(1, "订单"),

    MATCHING(2, "匹配"),

    SYS_NOTICE(10, "系统通知"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    public static MsgBizType getByCode (Integer code) {

        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}

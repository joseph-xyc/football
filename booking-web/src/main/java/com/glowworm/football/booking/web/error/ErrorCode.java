package com.glowworm.football.booking.web.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-02 10:38
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DEFAULT_ERROR(9000, "系统异常, 请稍后再试~");

    private final Integer code;

    private final String desc;

}

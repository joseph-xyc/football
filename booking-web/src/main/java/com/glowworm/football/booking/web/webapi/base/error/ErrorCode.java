package com.glowworm.football.booking.web.webapi.base.error;

import com.glowworm.football.booking.domain.exception.ICmOrderError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-02 10:38
 * 全局异常枚举
 */

@Getter
@AllArgsConstructor
public enum ErrorCode implements ICmOrderError {

    DEFAULT_ERROR(9000, "系统异常, 请稍后再试~");

    private final Integer code;

    private final String message;

}

package com.glowworm.football.booking.domain.exception;

import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-17 18:32
 * 业务校验相关异常,都应该以此为出口
 */
public class ValidateException extends RuntimeException {

    @Getter
    private Integer code;

    @Getter
    private String message;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException (ICmOrderError errorCode) {
        this(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}

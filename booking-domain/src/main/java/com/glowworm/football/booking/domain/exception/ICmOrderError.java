package com.glowworm.football.booking.domain.exception;

/**
 * @author xuyongchang
 * @date 2023-04-17 18:36
 */
public interface ICmOrderError {

    /**
     * 返回异常编号
     * @return 异常编号
     */
    Integer getCode ();

    /**
     * 返回异常msg（不是堆栈信息）
     * @return 异常message
     */
    String getMessage ();
}

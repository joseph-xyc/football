package com.glowworm.football.booking.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyongchang
 * @date 2023-04-02 10:10
 */
@Data
@NoArgsConstructor
public class Response<E> implements Serializable {

    private E result;

    private Long currentTime;

    private Integer code;

    private String message;

    private String traceId;

    public static <E> Response<E> success (E result) {

        Response<E> response = new Response<>();
        response.setCode(0);
        response.setMessage("success");
        response.setResult(result);
        response.setCurrentTime(System.currentTimeMillis());

        return response;
    }

    public static <E> Response<E> fail (Integer code, String message) {

        Response<E> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        response.setCurrentTime(System.currentTimeMillis());

        return response;
    }

}

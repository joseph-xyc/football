package com.glowworm.football.booking.web.web_service;

import com.glowworm.football.booking.domain.response.Response;
import com.glowworm.football.booking.web.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author xuyongchang
 * @date 2023-04-02 10:33
 */
@Slf4j
@Service
public class WebApiExceptionHandler {

    public Response<String> doCustomize (HttpServletRequest request, Exception exception) {

        return doCustomize(request, exception, ErrorCode.DEFAULT_ERROR);
    }

    public Response<String> doCustomize (HttpServletRequest request, Exception exception, ErrorCode errorCode) {

        // web端traceId
        String webTraceId = webTraceId();

        // 日志/埋点
        printLog(webTraceId, request, exception);

        // 语义化响应
        return customizeResponse(webTraceId, exception, errorCode);
    }

    private void printLog (String webTraceId, HttpServletRequest request, Exception exception) {

        log.error(request.getRequestURI() + " error, webTraceId: {}", webTraceId, exception);
    }

    private Response<String> customizeResponse (String webTraceId, Exception exception, ErrorCode errorCode) {

        // 语义化文案
        String customizeMessage = customizeMessage(webTraceId, exception, errorCode);

        // 封装响应
        Response<String> response = Response.fail(errorCode.getCode(), customizeMessage);
        response.setResult(webTraceId);
        return response;
    }

    /**
     * 语义化错误文案
     * 给C端用户返回一个文案化的简略错误文案, 以屏蔽不友好的错误堆栈
     * @param webTraceId traceId
     * @param exception 异常类型
     * @param errorCode ErrorCode
     * @return 语义化错误文案
     */
    private String customizeMessage (String webTraceId, Exception exception, ErrorCode errorCode) {

        if (useExceptionMessage(exception)) {
            return exception.getMessage();
        }

        return String.format("%s: [%s]", errorCode.getDesc(), webTraceId);
    }

    private boolean useExceptionMessage (Exception exception) {

        // 定制的异常类
        boolean customizeType = exception instanceof IllegalArgumentException;

        // 友好的文案 (暂时仅判断是否不包含exception字符串，可能存在误判)
        boolean friendlyText = Strings.isNotBlank(exception.getMessage()) && !exception.getMessage().contains("exception");

        return customizeType || friendlyText;
    }

    public String webTraceId() {

        return UUID.randomUUID().toString();
    }

}

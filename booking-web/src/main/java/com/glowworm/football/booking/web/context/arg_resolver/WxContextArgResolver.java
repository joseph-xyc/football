package com.glowworm.football.booking.web.context.arg_resolver;

import com.glowworm.football.booking.domain.context.WxContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuyongchang
 * @date 2023/3/27
 * 将微信ctx对象注入进controller的方法中
 *
 */
@Component
public class WxContextArgResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.getParameterType().equals(WxContext.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Object request = webRequest.getNativeRequest();
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            return httpRequest.getAttribute("context");
        }
        return new Object();
    }
}

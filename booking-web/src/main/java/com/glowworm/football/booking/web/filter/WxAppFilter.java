package com.glowworm.football.booking.web.filter;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.web.config.WebConfig;
import com.glowworm.football.booking.web.webapi.base.context.WxContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuyongchang
 * @date 2023/3/27
 * 微信小程序的基本Filter
 * 将用户的info封装为ctx上下文, 供后续controller中使用
 */
@Slf4j
@Component
public class WxAppFilter implements Filter {

    @Autowired
    private WxContextBuilder wxContextBuilder;
    @Autowired
    private WebConfig config;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 封装ctx
        buildCtx(servletRequest, servletResponse);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void buildCtx (ServletRequest servletRequest, ServletResponse servletResponse) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        WxContext ctx = wxContextBuilder.build(request, response);

        // 放进attribute
        request.setAttribute(config.getContextKey(), ctx);
    }
}

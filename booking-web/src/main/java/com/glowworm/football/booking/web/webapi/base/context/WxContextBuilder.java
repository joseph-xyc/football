package com.glowworm.football.booking.web.webapi.base.context;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.web.config.WebConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuyongchang
 * @date 2023/3/27
 * WxContext的构建服务
 */
@Slf4j
@Service
public class WxContextBuilder {

    @Autowired
    private WebConfig config;

    public WxContext build (HttpServletRequest request, HttpServletResponse response) {

        return WxContext.builder()
                .openId(getOpenId(request))
                .unionId(getUnionId(request))
                .wxSource(getWxSource(request))
                .build();
    }

    private String getOpenId (HttpServletRequest request) {

        return request.getHeader(config.getOpenIdKey());
    }

    private String getUnionId (HttpServletRequest request) {

        return request.getHeader(config.getUnionIdKey());
    }

    private String getWxSource (HttpServletRequest request) {

        return request.getHeader(config.getWxSourceKey());
    }

}

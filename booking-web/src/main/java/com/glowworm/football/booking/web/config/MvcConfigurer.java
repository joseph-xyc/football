package com.glowworm.football.booking.web.config;

import com.glowworm.football.booking.web.context.arg_resolver.WxContextArgResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/27
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private WxContextArgResolver wxContextArgResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(wxContextArgResolver);
    }
}

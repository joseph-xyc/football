package com.glowworm.football.booking.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuyongchang
 * @date 2023/3/27
 * web层的filter配置
 */
@Configuration
public class FilterConfig {

    @Autowired
    private WxAppFilter wxAppFilter;

    @Bean
    public FilterRegistrationBean<WxAppFilter> wxAppFilterBean() {
        FilterRegistrationBean<WxAppFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(wxAppFilter);
        registrationBean.addUrlPatterns("/web_api/*");
        return registrationBean;
    }
}

package com.glowworm.football.booking.web.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuyongchang
 * @date 2023/3/27
 * web层配置项
 */
@Data
@Configuration
public class WebConfig {

    @Value("ctx")
    private String contextKey;

    @Value("X-WX-OPENID")
    private String openIdKey;

    @Value("X-WX-UNIONID")
    private String unionIdKey;

    @Value("X-WX-SOURCE")
    private String wxSourceKey;
}

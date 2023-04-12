package com.glowworm.football.booking.service.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuyongchang
 * @date 2023-04-02 09:52
 */
@Data
@Configuration
public class UserConfig {

    @Value("${user.visitor.user.id:-999}")
    private Long visitorUserId;

    @Value("${user.visitor.open.id:-999}")
    private String visitorOpenId;

    @Value("${user.visitor.name:游客}")
    private String visitorName;
}

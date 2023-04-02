package com.glowworm.football.booking.service.account.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuyongchang
 * @date 2023-04-02 09:52
 */
@Data
@Configuration
public class AccountConfig {

    @Value("${account.visitor.account.id:-999}")
    private Long visitorAccountId;

    @Value("${account.visitor.open.id:-999}")
    private String visitorOpenId;

    @Value("${account.visitor.name:游客}")
    private String visitorName;
}

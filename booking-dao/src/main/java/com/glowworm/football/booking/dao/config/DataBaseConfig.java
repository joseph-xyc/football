package com.glowworm.football.booking.dao.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuyongchang
 * @date 2023/3/16
 */
@Slf4j
@Configuration
@MapperScan("com.glowworm.football.booking.dao.mapper")
public class DataBaseConfig {


}

package com.glowworm.football.booking.dao.config;

import com.glowworm.football.booking.dao.util.YmlPropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xuyongchang
 * @date 2023/3/16
 */
@Slf4j
@Configuration
@PropertySource(name = "datasource", value = "classpath:datasource.yml", factory = YmlPropertySourceFactory.class)
@MapperScan("com.glowworm.football.booking.dao.mapper")
public class DataBaseConfig {


}

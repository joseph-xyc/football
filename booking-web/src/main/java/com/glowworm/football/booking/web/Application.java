package com.glowworm.football.booking.web;

import com.glowworm.football.booking.dao.util.YmlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author xuyongchang
 * @date 2023/3/16
 */
@SpringBootApplication(scanBasePackages = {"com.glowworm.football"})
@PropertySource(value = {"classpath:team.yml"}, factory = YmlPropertySourceFactory.class)
public class Application {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT")));

        SpringApplication.run(Application.class, args);
    }
}

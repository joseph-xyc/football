package com.glowworm.football.booking.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author xuyongchang
 * @date 2023/3/16
 */
@SpringBootApplication(scanBasePackages = {"com.glowworm.football"})
public class Application {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT")));

        SpringApplication.run(Application.class, args);
    }
}

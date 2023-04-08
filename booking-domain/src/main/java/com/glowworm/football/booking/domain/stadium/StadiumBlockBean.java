package com.glowworm.football.booking.domain.stadium;

import com.glowworm.football.booking.domain.stadium.enums.StadiumBlockStatus;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:39
 * 球场-场地
 */
public class StadiumBlockBean {

    private Long id;

    private Long stadiumId;

    private String blockName;

    private Integer scaleType;

    private Integer swardType;

    private StadiumBlockStatus blockStatus;
}

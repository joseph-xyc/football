package com.glowworm.football.booking.domain.stadium;

import com.glowworm.football.booking.domain.stadium.enums.ScaleType;
import com.glowworm.football.booking.domain.stadium.enums.StadiumBlockStatus;
import com.glowworm.football.booking.domain.stadium.enums.SwardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:39
 * 球场-场地
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StadiumBlockBean {

    private Long id;

    private Long stadiumId;

    private String blockName;

    private ScaleType scaleType;

    private SwardType swardType;

    private StadiumBlockStatus blockStatus;
}

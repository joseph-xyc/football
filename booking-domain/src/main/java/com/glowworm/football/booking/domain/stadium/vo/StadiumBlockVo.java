package com.glowworm.football.booking.domain.stadium.vo;

import com.glowworm.football.booking.domain.stadium.enums.ScaleType;
import com.glowworm.football.booking.domain.stadium.enums.StadiumBlockStatus;
import com.glowworm.football.booking.domain.stadium.enums.SwardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-11 18:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StadiumBlockVo {

    private Long id;

    private Long stadiumId;

    private String blockName;

    private Integer scaleType;

    private String scaleTypeDesc;

    private Integer swardType;

    private String swardTypeDesc;
}

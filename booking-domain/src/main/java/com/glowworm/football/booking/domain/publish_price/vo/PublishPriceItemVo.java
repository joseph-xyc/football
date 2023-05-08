package com.glowworm.football.booking.domain.publish_price.vo;

import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-05-08 15:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishPriceItemVo {

    private ScheduleClock clockBegin;

    private ScheduleClock clockEnd;

    private Integer price;
}

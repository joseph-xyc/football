package com.glowworm.football.booking.domain.publish_price.query;

import com.glowworm.football.booking.domain.publish_price.enums.Week;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import lombok.Builder;
import lombok.Data;

/**
 * @author xuyongchang
 * @date 2023-05-08 18:27
 */
@Data
@Builder
public class QueryPublishPrice {

    private Long blockId;

    private Week week;

    private ScheduleClock clockBegin;

}

package com.glowworm.football.booking.domain.stadium.query;

import lombok.Builder;
import lombok.Data;

/**
 * @author xuyongchang
 * @date 2023-04-08 15:13
 */
@Data
@Builder
public class QueryStadium {

    private Long id;

    private Long blockId;

    private String stadiumName;
}

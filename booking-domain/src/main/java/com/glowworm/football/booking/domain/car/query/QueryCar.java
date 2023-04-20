package com.glowworm.football.booking.domain.car.query;

import lombok.Builder;
import lombok.Data;

/**
 * @author xuyongchang
 * @date 2023-04-20 17:24
 * 发车查询类
 */
@Data
@Builder
public class QueryCar {

    private Long scheduleId;
}

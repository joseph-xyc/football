package com.glowworm.football.booking.domain.stadium.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-23 20:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombineScheduleVo {

    private StadiumVo stadium;

    private StadiumBlockVo block;

    private ScheduleVo schedule;

}

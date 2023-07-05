package com.glowworm.football.booking.domain.common.vo;

import com.glowworm.football.booking.domain.publish_price.enums.Week;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-07-05 17:51
 * 球场排期中的日期
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekForSchedule {

    private String dateStr;

    private Week week;

    private Integer isWeekEnd;

}

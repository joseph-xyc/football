package com.glowworm.football.booking.domain.stadium.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:26
 * 球场-场地-时刻
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleVo {

    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Timestamp date;

    private String clockBegin;

    private String clockEnd;

    private Integer isAfternoon;

    private String weekName;

    private Integer isWeekend;

    private Integer status;
}

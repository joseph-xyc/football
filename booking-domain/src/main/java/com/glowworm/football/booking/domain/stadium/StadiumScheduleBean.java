package com.glowworm.football.booking.domain.stadium;

import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:27
 * 球场-场地-模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StadiumScheduleBean {

    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Timestamp date;

    private ScheduleClock clockBegin;

    private ScheduleClock clockEnd;

    private ScheduleStatus status;
}

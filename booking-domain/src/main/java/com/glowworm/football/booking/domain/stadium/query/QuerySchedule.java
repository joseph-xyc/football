package com.glowworm.football.booking.domain.stadium.query;

import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:31
 * 球场-场地-时刻表-查询模型
 */
@Data
@Builder
public class QuerySchedule {

    private Long id;

    private List<Long> ids;

    private Long stadiumId;

    private Long blockId;

    private Long date;

    private String dateBegin;

    private String dateEnd;

    private Timestamp dateBeginTimestamp;

    private Timestamp dateEndTimestamp;

    private List<ScheduleStatus> status;

    private Integer isAfternoon;

}

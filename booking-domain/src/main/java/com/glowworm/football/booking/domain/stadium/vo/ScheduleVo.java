package com.glowworm.football.booking.domain.stadium.vo;

import com.glowworm.football.booking.domain.matching.vo.MatchingVo;
import com.glowworm.football.booking.domain.stadium.enums.ScaleType;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

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

    private String blockName;

    private ScaleType scaleType;

    private Timestamp date;

    private String clockBegin;

    private String clockEnd;

    private Integer isAfternoon;

    private String weekName;

    private Integer isWeekend;

    private ScheduleStatus status;

    private Integer price;

    private List<TeamSimpleVo> teams;

    private List<MatchingVo> matched;

    private List<MatchingVo> matching;

    private Integer matchingCount;

    private Integer hasMatching;

    private Integer isWholeBooking;

}

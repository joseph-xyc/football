package com.glowworm.football.booking.domain.matching.vo;

import com.glowworm.football.booking.domain.matching.enums.MatchingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-08-14 18:43
 * 匹配vo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingVo {

    private Long id;

    private Long scheduleId;

    private Long userId;

    private String username;

    private String avatar;

    private Timestamp matchingTime;

    private MatchingStatus matchingStatus;
}

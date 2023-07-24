package com.glowworm.football.booking.domain.matching.query;

import com.glowworm.football.booking.domain.matching.enums.MatchingStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-07-20 20:56
 */
@Data
@Builder
public class QueryMatching {

    private List<Long> scheduleIds;

    private MatchingStatus matchingStatus;
}

package com.glowworm.football.booking.domain.matching.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-07-25 17:09
 * 散人匹配表单模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingFormVo {

    private Long scheduleId;
}

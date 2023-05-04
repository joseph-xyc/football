package com.glowworm.football.booking.domain.team.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-05-04 14:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamSimpleVo {

    private Long id;

    private String name;

    private String logo;
}

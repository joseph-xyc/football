package com.glowworm.football.booking.service.team.impl;

import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-05-04 14:40
 */
@Data
@Component
@ConfigurationProperties(prefix = "team")
public class TeamConfig {

    private List<TeamSimpleVo> randomTeam;
}

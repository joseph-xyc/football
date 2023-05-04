package com.glowworm.football.booking.service.team;

import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;

import java.util.List;
import java.util.Map;

/**
 * @author xuyongchang
 * @date 2023-05-04 14:37
 * 球队相关查询api
 */
public interface ITeamService {

    List<TeamSimpleVo> getRandomTeams ();

    Map<Long, TeamSimpleVo> queryRandomTeam (List<Long> randomTeamIds);

}

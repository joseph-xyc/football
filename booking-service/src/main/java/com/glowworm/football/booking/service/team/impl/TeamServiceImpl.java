package com.glowworm.football.booking.service.team.impl;

import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.service.team.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-04 14:38
 */
@Slf4j
@Service
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private TeamConfig teamConfig;

    @Override
    public List<TeamSimpleVo> getRandomTeams() {


        return teamConfig.getRandomTeam();
    }

    @Override
    public Map<Long, TeamSimpleVo> queryRandomTeam(List<Long> randomTeamIds) {

        List<TeamSimpleVo> result = queryRandomTeamList(randomTeamIds);
        return result.stream().collect(Collectors.toMap(TeamSimpleVo::getId, Function.identity()));
    }

    @Override
    public List<TeamSimpleVo> queryRandomTeamList(List<Long> randomTeamIds) {

        if (CollectionUtils.isEmpty(randomTeamIds)) {
            return Collections.emptyList();
        }

        List<TeamSimpleVo> randomTeams = teamConfig.getRandomTeam();

        Map<Long, TeamSimpleVo> teamMap = randomTeams.stream().collect(Collectors.toMap(TeamSimpleVo::getId, Function.identity()));

        return randomTeamIds.stream()
                .map(teamMap::get)
                .collect(Collectors.toList());
    }

    @Override
    public TeamSimpleVo getDefaultTeam() {
        return teamConfig.getDefaultTeam();
    }
}

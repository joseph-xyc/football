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

        if (CollectionUtils.isEmpty(randomTeamIds)) {
            return Collections.emptyMap();
        }

        List<TeamSimpleVo> randomTeams = teamConfig.getRandomTeam();
        List<TeamSimpleVo> result = randomTeams.stream()
                .filter(item -> randomTeamIds.contains(item.getId()))
                .collect(Collectors.toList());

        return result.stream().collect(Collectors.toMap(TeamSimpleVo::getId, Function.identity()));
    }
}

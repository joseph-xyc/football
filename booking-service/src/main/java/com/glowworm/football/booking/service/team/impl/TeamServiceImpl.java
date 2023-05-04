package com.glowworm.football.booking.service.team.impl;

import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.service.team.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

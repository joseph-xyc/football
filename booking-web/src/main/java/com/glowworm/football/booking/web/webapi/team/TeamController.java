package com.glowworm.football.booking.web.webapi.team;

import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.service.team.ITeamService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-05-04 14:32
 * 球队相关api
 */
@RestController
@RequestMapping("/api/web_api/team")
public class TeamController extends BaseController {

    @Autowired
    private ITeamService teamService;

    @GetMapping(value = "/random_list")
    public Response<List<TeamSimpleVo>> getRandomTeams () {

        List<TeamSimpleVo> teams = teamService.getRandomTeams();
        return Response.success(teams);
    }
}

package com.glowworm.football.booking.web.webapi.stadium;

import com.glowworm.football.booking.domain.context.WxContext;
import com.glowworm.football.booking.domain.response.Response;
import com.glowworm.football.booking.domain.stadium.QueryStadiumVo;
import com.glowworm.football.booking.domain.stadium.StadiumBean;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.web.webapi.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-08 15:11
 * 球场
 */
@RestController
@RequestMapping("/api/web_api/stadium")
public class StadiumController extends BaseController {

    @Autowired
    private IStadiumService stadiumService;

    @GetMapping(value = "/list")
    public Response<List<StadiumBean>> queryList (WxContext ctx, QueryStadiumVo query) {

        return Response.success(stadiumService.queryList(ctx, query));
    }
}

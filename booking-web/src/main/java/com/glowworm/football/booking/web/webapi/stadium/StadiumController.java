package com.glowworm.football.booking.web.webapi.stadium;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.stadium.query.QueryStadium;
import com.glowworm.football.booking.domain.stadium.vo.StadiumInfoVo;
import com.glowworm.football.booking.domain.stadium.vo.StadiumVo;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import com.glowworm.football.booking.web.webapi.stadium.service.StadiumWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private StadiumWebService stadiumWebService;

    @GetMapping(value = "/list")
    public Response<List<StadiumVo>> queryList (WxContext ctx, QueryStadium query) {

        return Response.success(stadiumWebService.queryList(getUser(ctx), query));
    }

    @GetMapping(value = "/info/{id}")
    public Response<StadiumInfoVo> getInfo (WxContext ctx, @PathVariable(value = "id") Long id) {

        return Response.success(stadiumWebService.getDetail(ctx, id));
    }

}

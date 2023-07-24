package com.glowworm.football.booking.web.webapi.stadium;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.stadium.vo.CombineScheduleVo;
import com.glowworm.football.booking.domain.stadium.vo.ScheduleVo;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import com.glowworm.football.booking.web.webapi.stadium.service.StadiumWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-14 11:34
 * 球场-时刻表
 */
@RestController
@RequestMapping("/api/web_api/stadium/schedule")
public class ScheduleController extends BaseController {

    @Autowired
    private StadiumWebService stadiumWebService;

    @GetMapping(value = "/list")
    public Response<List<ScheduleVo>> queryList (WxContext ctx, QuerySchedule query) {

        return Response.success(stadiumWebService.queryScheduleList(getUser(ctx), query));
    }

    @GetMapping(value = "/combine_info")
    public Response<CombineScheduleVo> combineInfo (WxContext ctx, QuerySchedule query) {

        return Response.success(stadiumWebService.getCombineScheduleVo(query));
    }
}

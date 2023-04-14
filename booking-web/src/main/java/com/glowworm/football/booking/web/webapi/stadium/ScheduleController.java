package com.glowworm.football.booking.web.webapi.stadium;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.stadium.vo.StadiumScheduleVo;
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
public class ScheduleController {

    @Autowired
    private StadiumWebService stadiumWebService;

    @GetMapping(value = "/list")
    public Response<List<StadiumScheduleVo>> queryList (WxContext ctx, QuerySchedule query) {

        return Response.success(stadiumWebService.queryScheduleList(query));
    }
}

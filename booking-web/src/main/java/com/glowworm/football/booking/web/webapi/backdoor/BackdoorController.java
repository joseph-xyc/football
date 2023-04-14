package com.glowworm.football.booking.web.webapi.backdoor;

import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleActionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xuyongchang
 * @date 2023-04-13 18:32
 * 后门口子
 */
@RestController
@RequestMapping("/api/web_api/backdoor")
public class BackdoorController {

    @Autowired
    private IStadiumScheduleActionService scheduleActionService;

    @GetMapping(value = "/append_schedule")
    public Response<String> appendSchedule (String endDate,
                                            @RequestParam(required = false) Long stadiumId,
                                            @RequestParam(required = false) Long blockId) {

        scheduleActionService.appendSchedule(endDate, stadiumId, blockId);
        return Response.success(Strings.EMPTY);
    }
}

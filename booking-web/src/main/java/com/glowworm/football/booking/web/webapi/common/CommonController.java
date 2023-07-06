package com.glowworm.football.booking.web.webapi.common;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.common.vo.WeekForSchedule;
import com.glowworm.football.booking.domain.publish_price.enums.Week;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-07-05 16:10
 * 公用的api
 */
@RestController
@RequestMapping("/api/web_api/common")
public class CommonController extends BaseController {

    @GetMapping(value = "/week_list")
    public Response<List<WeekForSchedule>> getWeekList (WxContext ctx) {

        Timestamp now = DateUtils.getNow();

        // 起止时间
        Timestamp beginDay = DateUtils.getBeginDayOfWeek(now);
        Timestamp endDay = DateUtils.getEndDayOfWeek(DateUtils.getSomeDayAfter(now, 7));

        // 2周范围
        List<Timestamp> days = DateUtils.getEachDays(beginDay, endDay);

        List<WeekForSchedule> weekList = days.stream().map(item -> {

            Week week = Week.getByCode(DateUtils.getWeekDay(item));
            Integer isWeekEnd = TrueFalse.getByBoolean(Week.isWeekendForSchedule(week.getCode())).getCode();

            return WeekForSchedule.builder()
                    .dateStr(DateUtils.getTimestamp2String(item, "MM-dd"))
                    .week(week)
                    .isWeekEnd(isWeekEnd)
                    .build();
        }).collect(Collectors.toList());

        return Response.success(weekList);
    }
}
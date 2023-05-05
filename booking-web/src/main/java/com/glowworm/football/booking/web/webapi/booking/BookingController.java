package com.glowworm.football.booking.web.webapi.booking;

import com.glowworm.football.booking.domain.booking.query.QueryBooking;
import com.glowworm.football.booking.domain.booking.vo.BookingFormVo;
import com.glowworm.football.booking.domain.booking.vo.BookingVo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.service.booking.IBookingActionService;
import com.glowworm.football.booking.service.booking.IBookingService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import com.glowworm.football.booking.web.webapi.booking.service.BookingWebService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-17 17:27
 * 预约服务
 */
@RestController
@RequestMapping("/api/web_api/booking")
public class BookingController extends BaseController {

    @Autowired
    private IBookingActionService bookingActionService;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private BookingWebService bookingWebService;

    @GetMapping("/list_in_schedule")
    public Response<List<BookingVo>> booking (WxContext ctx, QueryBooking query) {

        List<BookingVo> bookingList = bookingService.query(query);
        return Response.success(bookingWebService.enhanceTeamSimpleInfo(getUser(ctx), bookingList));
    }

    @PostMapping(value = "")
    public Response<Long> booking (WxContext ctx, @RequestBody BookingFormVo formVo) {

        Long id = bookingActionService.booking(getUser(ctx), formVo);
        return Response.success(id);
    }

    @PostMapping(value = "/cancel/{id}")
    public Response<String> cancel (WxContext ctx, @PathVariable(value = "id") Long id) {

        bookingActionService.cancel(getUser(ctx), id);
        return Response.success(Strings.EMPTY);
    }
}

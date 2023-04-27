package com.glowworm.football.booking.web.webapi.booking;

import com.glowworm.football.booking.domain.booking.vo.BookingFormVo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.service.booking.IBookingActionService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "")
    public Response<String> booking (WxContext ctx, @RequestBody BookingFormVo formVo) {

        bookingActionService.booking(getUser(ctx), formVo);
        return Response.success(Strings.EMPTY);
    }

    @PostMapping(value = "/cancel/{id}")
    public Response<String> cancel (WxContext ctx, @PathVariable(value = "id") Long id) {

        bookingActionService.cancel(getUser(ctx), id);
        return Response.success(Strings.EMPTY);
    }
}

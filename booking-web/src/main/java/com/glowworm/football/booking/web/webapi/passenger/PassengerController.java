package com.glowworm.football.booking.web.webapi.passenger;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.demo.query.QueryDemo;
import com.glowworm.football.booking.domain.common.demo.vo.DemoVo;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-27 16:15
 * 乘客相关的ctl
 */
@RestController
@RequestMapping("/api/web_api/passenger")
public class PassengerController extends BaseController {

    @GetMapping(value = "/list")
    public Response<List<DemoVo>> queryList (WxContext ctx, QueryDemo query) {

        return Response.success(null);
    }
}

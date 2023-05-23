package com.glowworm.football.booking.web.webapi.user;

import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.domain.user.enums.Position;
import com.glowworm.football.booking.domain.user.enums.Style;
import com.glowworm.football.booking.domain.user.vo.CreateUserFormVo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.service.stadium.IStadiumCollectService;
import com.glowworm.football.booking.service.user.IUserService;
import com.glowworm.football.booking.service.user.IUserVisitLogService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author xuyongchang
 * @date 2023-04-01 16:55
 * 用户相关api
 */
@RestController
@RequestMapping("/api/web_api/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IStadiumCollectService stadiumCollectService;

    @Autowired
    private IUserVisitLogService userVisitLogService;

    @GetMapping(value = "/user_info")
    public Response<UserBean> userInfo (WxContext ctx) {

        return Response.success(userService.userInfo(ctx.getOpenId()));
    }

    @PostMapping(value = "/register")
    public Response<String> register (WxContext ctx, @RequestBody CreateUserFormVo formVo) {

        userService.registerUser(ctx, formVo);

        return Response.success(Strings.EMPTY);
    }

    @PostMapping(value = "/update")
    public Response<String> update (WxContext ctx, @RequestBody CreateUserFormVo formVo) {

        userService.updateUser(ctx, formVo);

        return Response.success(Strings.EMPTY);
    }

    @GetMapping(value = "/pos")
    public Response<List<Position>> pos (WxContext ctx) {

        return Response.success(Arrays.stream(Position.values()).collect(Collectors.toList()));
    }

    @GetMapping(value = "/styles")
    public Response<List<Style>> styles (WxContext ctx) {

        return Response.success(Arrays.stream(Style.values()).collect(Collectors.toList()));
    }

    @PostMapping(value = "/collect/stadium/{id}")
    public Response<String> collectStadium (WxContext ctx, @PathVariable(value = "id") Long id) {

        stadiumCollectService.collectStadium(getUser(ctx), id);
        return Response.success(Strings.EMPTY);
    }

    @PostMapping(value = "/unCollect/stadium/{id}")
    public Response<String> unCollectStadium (WxContext ctx, @PathVariable(value = "id") Long id) {

        stadiumCollectService.unCollectStadium(getUser(ctx), id);
        return Response.success(Strings.EMPTY);
    }

    @GetMapping(value = "/visit_log")
    public Response<String> visitLog (WxContext ctx) {

        userVisitLogService.log(ctx);

        return Response.success(Strings.EMPTY);
    }
}

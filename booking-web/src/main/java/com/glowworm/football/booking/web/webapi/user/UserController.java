package com.glowworm.football.booking.web.webapi.user;

import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.domain.user.vo.CreateUserVo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.service.user.IUserService;
import com.glowworm.football.booking.service.user.IUserVisitLogService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author xuyongchang
 * @date 2023-04-01 16:55
 */
@RestController
@RequestMapping("/api/web_api/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserVisitLogService userVisitLogService;

    @GetMapping(value = "/user_info")
    public Response<UserBean> userInfo (WxContext ctx) {

        return Response.success(userService.userInfo(ctx.getOpenId()));
    }

    @PostMapping(value = "/register_user")
    public Response<String> registerUser (WxContext ctx, @RequestBody CreateUserVo createUserVo) {

        UserBean userBean = UserBean.builder()
                .username(createUserVo.getUsername())
                .avatar(createUserVo.getAvatar())
                .sex(createUserVo.getSex())
                .build();
        userService.registerUser(ctx, userBean);

        return Response.success(Strings.EMPTY);
    }

    @GetMapping(value = "/visit_log")
    public Response<String> visitLog (WxContext ctx) {

        userVisitLogService.log(ctx);

        return Response.success(Strings.EMPTY);
    }
}

package com.glowworm.football.booking.web.webapi.base;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuyongchang
 * @date 2023-04-02 10:07
 */

@RestController
public class BaseController {

    @Autowired
    private WebApiExceptionHandler webApiExceptionHandler;

    @Autowired
    private IUserService userService;

    public UserBean getUser (WxContext ctx) {

        return userService.userInfo(ctx.getOpenId());
    }

    @ExceptionHandler({ Exception.class })
    public @ResponseBody Response<String> handleException (HttpServletRequest req, Exception exception) {

        return webApiExceptionHandler.doCustomize(req, exception);
    }
}

package com.glowworm.football.booking.web.webapi.account;

import com.glowworm.football.booking.domain.account.AccountBean;
import com.glowworm.football.booking.domain.account.CreateAccountVo;
import com.glowworm.football.booking.domain.context.WxContext;
import com.glowworm.football.booking.domain.response.Response;
import com.glowworm.football.booking.service.account.IAccountService;
import com.glowworm.football.booking.service.account.IAccountVisitLogService;
import com.glowworm.football.booking.web.webapi.BaseController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author xuyongchang
 * @date 2023-04-01 16:55
 */
@RestController
@RequestMapping("/api/web_api/account")
public class AccountController extends BaseController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountVisitLogService accountVisitLogService;

    @GetMapping(value = "/account_info")
    public Response<AccountBean> accountInfo (WxContext ctx) {

        return Response.success(accountService.getAccount(ctx.getOpenId()));
    }

    @PostMapping(value = "/register_account")
    public Response<String> registerAccount (WxContext ctx, @RequestBody CreateAccountVo createAccountVo) {

        AccountBean accountBean = AccountBean.builder()
                .username(createAccountVo.getUsername())
                .avatar(createAccountVo.getAvatar())
                .sex(createAccountVo.getSex())
                .build();
        accountService.registerAccount(ctx, accountBean);

        return Response.success(Strings.EMPTY);
    }

    @GetMapping(value = "/visit_log")
    public Response<String> visitLog (WxContext ctx) {

        accountVisitLogService.log(ctx);

        return Response.success(Strings.EMPTY);
    }
}

package com.glowworm.football.booking.web.webapi.account;

import com.glowworm.football.booking.domain.account.AccountBean;
import com.glowworm.football.booking.domain.account.CreateAccountVo;
import com.glowworm.football.booking.domain.context.WxContext;
import com.glowworm.football.booking.service.account.IAccountService;
import com.glowworm.football.booking.service.account.IAccountVisitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-01 16:55
 */
@RestController
@RequestMapping("/api/web_api/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountVisitLogService accountVisitLogService;

    @GetMapping(value = "/query_account")
    public List<AccountBean> queryAccount (WxContext ctx) {

        return accountService.queryAccount(ctx);
    }

    @GetMapping(value = "/get_account")
    public AccountBean getAccount (WxContext ctx) {

        return accountService.getAccount(ctx.getOpenId());
    }

    @PostMapping(value = "/register_account")
    public void registerAccount (WxContext ctx, @RequestBody CreateAccountVo createAccountVo) {

        AccountBean accountBean = AccountBean.builder()
                .username(createAccountVo.getUsername())
                .avatar(createAccountVo.getAvatar())
                .sex(createAccountVo.getSex())
                .build();
        accountService.registerAccount(ctx, accountBean);
    }

    @GetMapping(value = "/visit_log")
    public void visitLog (WxContext ctx) {

        accountVisitLogService.log(ctx);
    }
}

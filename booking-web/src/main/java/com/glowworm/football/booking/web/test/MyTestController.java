package com.glowworm.football.booking.web.test;

import com.glowworm.football.booking.domain.account.AccountBean;
import com.glowworm.football.booking.domain.account.CreateAccountVo;
import com.glowworm.football.booking.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author xuyongchang
 * @date 2023/3/16
 */
@RestController
@RequestMapping("/web_api/account")
public class MyTestController {

    @Autowired
    private IAccountService accountService;

    @GetMapping(value = "/query_account")
    public List<AccountBean> queryAccount () {

        return accountService.queryAccount();
    }

    @GetMapping(value = "/create_account")
    public void createAccount (CreateAccountVo createAccountVo) {

        AccountBean accountBean = AccountBean.builder()
                .username(createAccountVo.getUsername())
                .build();
        accountService.createAccount(accountBean);
    }
}

package com.glowworm.football.booking.web.test;

import com.glowworm.football.booking.domain.account.CreateAccountVo;
import com.glowworm.football.booking.domain.account.FtAccountPo;
import com.glowworm.football.booking.service.account.IAccountService;
import com.glowworm.football.booking.service.util.FtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public String queryAccount (String msg) {

        return accountService.queryTest(msg);
    }

    @GetMapping(value = "/create_account")
    public void createAccount (CreateAccountVo createAccountVo) {

        FtAccountPo po = FtUtil.copy(createAccountVo, FtAccountPo.class);
        accountService.save(po);
    }

    @GetMapping(value = "/update_account")
    public void updateAccount (CreateAccountVo createAccountVo) {

        FtAccountPo po = FtUtil.copy(createAccountVo, FtAccountPo.class);
        accountService.updateById(po);
    }
}

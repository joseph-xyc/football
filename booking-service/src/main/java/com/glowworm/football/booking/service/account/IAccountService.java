package com.glowworm.football.booking.service.account;

import com.glowworm.football.booking.domain.account.AccountBean;
import com.glowworm.football.booking.domain.context.WxContext;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
public interface IAccountService {

    AccountBean getAccount (String openId);

    List<AccountBean> queryAccount (WxContext ctx);

    void registerAccount (WxContext ctx, AccountBean accountBean);

}

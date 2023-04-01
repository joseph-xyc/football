package com.glowworm.football.booking.service.account;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glowworm.football.booking.dao.po.FtAccountPo;
import com.glowworm.football.booking.domain.account.AccountBean;
import com.glowworm.football.booking.domain.context.WxContext;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
public interface IAccountService extends IService<FtAccountPo> {

    AccountBean getAccount (String openId);

    List<AccountBean> queryAccount (WxContext ctx);

    void registerAccount (WxContext ctx, AccountBean accountBean);

    void createAccount (AccountBean accountBean);

}

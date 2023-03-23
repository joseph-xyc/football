package com.glowworm.football.booking.service.account;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glowworm.football.booking.dao.po.FtAccountPo;
import com.glowworm.football.booking.domain.account.AccountBean;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
public interface IAccountService extends IService<FtAccountPo> {


    List<AccountBean> queryAccount ();

    void createAccount (AccountBean accountBean);

}

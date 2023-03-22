package com.glowworm.football.booking.service.account;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glowworm.football.booking.domain.account.FtAccountPo;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
public interface IAccountService extends IService<FtAccountPo> {


    String queryTest (String msg);
}

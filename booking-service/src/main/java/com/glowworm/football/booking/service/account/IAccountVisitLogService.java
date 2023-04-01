package com.glowworm.football.booking.service.account;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glowworm.football.booking.dao.po.FtAccountVisitLogPo;
import com.glowworm.football.booking.domain.context.WxContext;

/**
 * @author xuyongchang
 * @date 2023-04-01 16:25
 */
public interface IAccountVisitLogService extends IService<FtAccountVisitLogPo> {

    void log (WxContext ctx);
}

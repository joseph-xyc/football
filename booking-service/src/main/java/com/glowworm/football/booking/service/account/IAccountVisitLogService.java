package com.glowworm.football.booking.service.account;

import com.glowworm.football.booking.domain.context.WxContext;

/**
 * @author xuyongchang
 * @date 2023-04-01 16:25
 */
public interface IAccountVisitLogService {

    void log (WxContext ctx);
}

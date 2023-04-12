package com.glowworm.football.booking.service.user;

import com.glowworm.football.booking.domain.common.context.WxContext;

/**
 * @author xuyongchang
 * @date 2023-04-01 16:25
 */
public interface IUserVisitLogService {

    void log (WxContext ctx);
}

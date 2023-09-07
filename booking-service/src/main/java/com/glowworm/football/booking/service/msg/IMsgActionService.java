package com.glowworm.football.booking.service.msg;

import com.glowworm.football.booking.domain.msg.MsgBean;

/**
 * @author xuyongchang
 * @date 2023-09-06 19:21
 */
public interface IMsgActionService {

    void newMsg (MsgBean msg);

    void readMsgSimplify (Long userId);
}

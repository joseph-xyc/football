package com.glowworm.football.booking.service.msg;

import com.glowworm.football.booking.dao.po.msg.FtMsgPo;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-09-06 19:19
 */
public interface IMsgService {

    List<FtMsgPo> getMsgList (Long userId);
}

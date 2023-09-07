package com.glowworm.football.booking.service.msg.impl;

import com.glowworm.football.booking.dao.mapper.FtMsgMapper;
import com.glowworm.football.booking.dao.po.msg.FtMsgPo;
import com.glowworm.football.booking.domain.msg.MsgBean;
import com.glowworm.football.booking.service.msg.IMsgActionService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuyongchang
 * @date 2023-09-07 10:50
 */
@Slf4j
@Service
public class MsgActionServiceImpl implements IMsgActionService {

    @Autowired
    private FtMsgMapper msgMapper;

    @Override
    public void newMsg(MsgBean msg) {

        FtMsgPo msgPo = FtMsgPo.builder()
                .userId(msg.getUserId())
                .msgSysType(msg.getMsgSysType())
                .msgBizType(msg.getMsgBizType())
                .content(msg.getContent())
                .build();
        msgMapper.insert(msgPo);

    }

    @Override
    public void readMsgSimplify(Long userId) {

    }
}

package com.glowworm.football.booking.service.msg.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtMsgMapper;
import com.glowworm.football.booking.dao.po.msg.FtMsgPo;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.msg.MsgBean;
import com.glowworm.football.booking.service.msg.IMsgActionService;
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

        FtMsgPo updateMsg = FtMsgPo.builder()
                .isRead(TrueFalse.TRUE.getCode())
                .build();
        msgMapper.update(updateMsg, Wrappers.lambdaQuery(FtMsgPo.class)
                .eq(FtMsgPo::getUserId, userId));
    }
}

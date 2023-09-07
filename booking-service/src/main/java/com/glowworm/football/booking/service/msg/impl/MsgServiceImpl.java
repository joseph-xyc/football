package com.glowworm.football.booking.service.msg.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtMsgMapper;
import com.glowworm.football.booking.dao.po.msg.FtMsgPo;
import com.glowworm.football.booking.service.msg.IMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-09-06 19:26
 */
@Slf4j
@Service
public class MsgServiceImpl implements IMsgService {

    @Autowired
    private FtMsgMapper msgMapper;

    @Override
    public List<FtMsgPo> getMsgList(Long userId) {

        if (Objects.isNull(userId)) {
            return Collections.emptyList();
        }

        return msgMapper.selectList(Wrappers.lambdaQuery(FtMsgPo.class)
                .eq(FtMsgPo::getUserId, userId)
                .orderByDesc(FtMsgPo::getDate));
    }
}

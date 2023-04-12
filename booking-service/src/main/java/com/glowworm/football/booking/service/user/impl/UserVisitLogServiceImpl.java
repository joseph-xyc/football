package com.glowworm.football.booking.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glowworm.football.booking.dao.mapper.FtUserVisitLogMapper;
import com.glowworm.football.booking.dao.po.user.FtUserVisitLogPo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.service.user.IUserVisitLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-01 16:27
 */

@Slf4j
@Service
public class UserVisitLogServiceImpl extends ServiceImpl<FtUserVisitLogMapper, FtUserVisitLogPo> implements IUserVisitLogService {

    @Override
    public void log(WxContext ctx) {

        if (Strings.isEmpty(ctx.getOpenId())) {
            return;
        }

        FtUserVisitLogPo log = this.getOne(Wrappers
                .lambdaQuery(FtUserVisitLogPo.class)
                .eq(FtUserVisitLogPo::getOpenId, ctx.getOpenId()));

        // 存在，则+1
        if (Objects.nonNull(log)) {
            this.updateById(FtUserVisitLogPo.builder()
                    .id(log.getId())
                    .count(log.getCount() + 1)
                    .build());
            return;
        }

        // 不存在，插入
        this.save(FtUserVisitLogPo.builder()
                .openId(ctx.getOpenId())
                .build());
    }
}

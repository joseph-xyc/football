package com.glowworm.football.booking.service.account.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glowworm.football.booking.dao.mapper.FtAccountVisitLogMapper;
import com.glowworm.football.booking.dao.po.FtAccountVisitLogPo;
import com.glowworm.football.booking.domain.context.WxContext;
import com.glowworm.football.booking.service.account.IAccountVisitLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-01 16:27
 */

@Slf4j
@Service
public class AccountVisitLogServiceImpl extends ServiceImpl<FtAccountVisitLogMapper, FtAccountVisitLogPo> implements IAccountVisitLogService {

    @Override
    public void log(WxContext ctx) {

        if (Strings.isEmpty(ctx.getOpenId())) {
            return;
        }

        FtAccountVisitLogPo log = this.getOne(Wrappers
                .lambdaQuery(FtAccountVisitLogPo.class)
                .eq(FtAccountVisitLogPo::getOpenId, ctx.getOpenId()));

        // 存在，则+1
        if (Objects.nonNull(log)) {
            this.updateById(FtAccountVisitLogPo.builder()
                    .id(log.getId())
                    .count(log.getCount() + 1)
                    .build());
            return;
        }

        // 不存在，插入
        this.save(FtAccountVisitLogPo.builder()
                .openId(ctx.getOpenId())
                .build());
    }
}

package com.glowworm.football.booking.service.account.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glowworm.football.booking.dao.mapper.FtAccountMapper;
import com.glowworm.football.booking.dao.po.FtAccountPo;
import com.glowworm.football.booking.domain.account.AccountBean;
import com.glowworm.football.booking.domain.context.WxContext;
import com.glowworm.football.booking.service.account.IAccountService;
import com.glowworm.football.booking.service.util.FtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<FtAccountMapper, FtAccountPo> implements IAccountService {

    @Override
    public List<AccountBean> queryAccount(WxContext ctx) {

        log.info("当前用户信息为: {}", ctx);
        List<FtAccountPo> accountPos = this.list();
        if (CollectionUtils.isEmpty(accountPos)) {
            return Collections.emptyList();
        }

        return accountPos.stream()
                .map(item -> FtUtil.copy(item, AccountBean.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createAccount(AccountBean accountBean) {

        FtAccountPo accountPo = FtUtil.copy(accountBean, FtAccountPo.class);
        this.save(accountPo);
    }
}

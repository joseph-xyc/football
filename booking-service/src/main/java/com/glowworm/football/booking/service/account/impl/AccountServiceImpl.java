package com.glowworm.football.booking.service.account.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glowworm.football.booking.dao.mapper.FtAccountMapper;
import com.glowworm.football.booking.domain.account.FtAccountPo;
import com.glowworm.football.booking.service.account.IAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Service
public class AccountServiceImpl extends ServiceImpl<FtAccountMapper, FtAccountPo> implements IAccountService {


    @Override
    public String queryTest(String msg) {

        List<FtAccountPo> list = list();
        list.forEach(System.out::println);
        return "AccountServiceImpl queryTest: " + msg;
    }
}

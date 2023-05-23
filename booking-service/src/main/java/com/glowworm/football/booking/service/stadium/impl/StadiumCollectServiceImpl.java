package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtStadiumCollectMapper;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumCollectPo;
import com.glowworm.football.booking.domain.stadium.enums.StadiumCollectStatus;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.stadium.IStadiumCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-05-23 16:16
 */
@Slf4j
@Service
public class StadiumCollectServiceImpl implements IStadiumCollectService {

    @Autowired
    private FtStadiumCollectMapper stadiumCollectMapper;

    @Override
    public List<FtStadiumCollectPo> queryStadiumCollect(UserBean user) {

        return stadiumCollectMapper.selectList(Wrappers.lambdaQuery(FtStadiumCollectPo.class)
                .eq(FtStadiumCollectPo::getUserId, user.getId()));
    }

    @Transactional
    @Override
    public void collectStadium(UserBean user, Long stadiumId) {

        LambdaQueryWrapper<FtStadiumCollectPo> query = Wrappers.lambdaQuery(FtStadiumCollectPo.class)
                .eq(FtStadiumCollectPo::getUserId, user.getId())
                .eq(FtStadiumCollectPo::getStadiumId, stadiumId);

        boolean exists = stadiumCollectMapper.exists(query);

        if (exists) {
            FtStadiumCollectPo collectPo = stadiumCollectMapper.selectOne(query);
            stadiumCollectMapper.updateById(FtStadiumCollectPo.builder()
                    .id(collectPo.getId())
                    .collectStatus(StadiumCollectStatus.VALID)
                    .build());
            return;
        }

        stadiumCollectMapper.insert(FtStadiumCollectPo.builder()
                .stadiumId(stadiumId)
                .userId(user.getId())
                .collectStatus(StadiumCollectStatus.VALID)
                .build());
    }

    @Transactional
    @Override
    public void unCollectStadium(UserBean user, Long stadiumId) {

        LambdaQueryWrapper<FtStadiumCollectPo> query = Wrappers.lambdaQuery(FtStadiumCollectPo.class)
                .eq(FtStadiumCollectPo::getUserId, user.getId())
                .eq(FtStadiumCollectPo::getStadiumId, stadiumId);

        stadiumCollectMapper.update(FtStadiumCollectPo.builder()
                .collectStatus(StadiumCollectStatus.INVALID)
                .build(), query);
    }
}

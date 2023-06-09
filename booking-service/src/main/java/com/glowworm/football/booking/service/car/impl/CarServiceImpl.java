package com.glowworm.football.booking.service.car.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtCarMapper;
import com.glowworm.football.booking.dao.po.car.FtCarPo;
import com.glowworm.football.booking.domain.car.enums.CarStatus;
import com.glowworm.football.booking.domain.car.query.QueryCar;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.car.ICarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-20 17:26
 */
@Slf4j
@Service
public class CarServiceImpl implements ICarService {

    @Autowired
    private FtCarMapper carMapper;

    @Override
    public List<FtCarPo> queryCar(UserBean user, QueryCar query) {

        return carMapper.selectList(Wrappers.lambdaQuery(FtCarPo.class)
                .in(FtCarPo::getCarStatus, CarStatus.WAITING, CarStatus.FULL, CarStatus.OVERLOAD)
                .eq(Objects.nonNull(query.getStadiumId()), FtCarPo::getStadiumId, query.getStadiumId())
                .eq(Objects.nonNull(query.getScheduleId()), FtCarPo::getScheduleId, query.getScheduleId()));
    }
}

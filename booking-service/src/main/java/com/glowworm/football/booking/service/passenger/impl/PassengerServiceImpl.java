package com.glowworm.football.booking.service.passenger.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtPassengerMapper;
import com.glowworm.football.booking.dao.po.passenger.FtPassengerPo;
import com.glowworm.football.booking.domain.passenger.enums.PassengerStatus;
import com.glowworm.football.booking.service.passenger.IPassengerService;
import com.glowworm.football.booking.service.util.MybatisUtils;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-04-27 16:23
 */
@Slf4j
@Service
public class PassengerServiceImpl implements IPassengerService {

    @Autowired
    private FtPassengerMapper passengerMapper;

    @Override
    public List<FtPassengerPo> queryPassengerOnBoard(Long carId) {

        Utils.isTrue(Objects.nonNull(carId), "车id不能为空");
        return passengerMapper.selectList(Wrappers.lambdaQuery(FtPassengerPo.class)
                .eq(FtPassengerPo::getPassengerStatus, PassengerStatus.GET_ON)
                .eq(FtPassengerPo::getCarId, carId));
    }

    @Override
    public Map<Long, List<FtPassengerPo>> queryPassengerOnBoard(List<Long> carIds) {

        if (CollectionUtils.isEmpty(carIds)) {
            return Collections.emptyMap();
        }

        List<FtPassengerPo> passengers = passengerMapper.selectList(Wrappers.lambdaQuery(FtPassengerPo.class)
                .eq(FtPassengerPo::getPassengerStatus, PassengerStatus.GET_ON)
                .in(FtPassengerPo::getCarId, carIds));

        return passengers.stream().collect(Collectors.groupingBy(FtPassengerPo::getCarId));
    }
}

package com.glowworm.football.booking.web.webapi.car.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtPassengerMapper;
import com.glowworm.football.booking.dao.po.car.FtCarPo;
import com.glowworm.football.booking.dao.po.passenger.FtPassengerPo;
import com.glowworm.football.booking.domain.car.vo.CarSimpleVo;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.passenger.enums.PassengerStatus;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-04-20 17:41
 */
@Slf4j
@Service
public class CarWebService {

    @Autowired
    private FtPassengerMapper passengerMapper;

    public List<CarSimpleVo> listInSchedule (UserBean user, List<FtCarPo> carPos) {

        if (CollectionUtils.isEmpty(carPos)) {
            return Collections.emptyList();
        }

        List<CarSimpleVo> carVos = carPos.stream().map(item -> CarSimpleVo.builder()
                        .id(item.getId())
                        .scheduleId(item.getScheduleId())
                        .userId(item.getUserId())
                        .carName(item.getCarName())
                        .carType(item.getCarType().getCode())
                        .carStatus(item.getCarStatus().getCode())
                        .isMyCar(TrueFalse.getByBoolean(item.getUserId().equals(user.getId())).getCode())
                        .carTopic(item.getCarTopic())
                        .build())
                .collect(Collectors.toList());

        // enhance可操作性
        enhanceOpt(user, carVos);

        return carVos;
    }

    public List<CarSimpleVo> po2vo (UserBean user, List<FtCarPo> carPos) {

        if (CollectionUtils.isEmpty(carPos)) {
            return Collections.emptyList();
        }

        return carPos.stream().map(item -> CarSimpleVo.builder()
                        .id(item.getId())
                        .scheduleId(item.getScheduleId())
                        .userId(item.getUserId())
                        .carName(item.getCarName())
                        .carType(item.getCarType().getCode())
                        .carStatus(item.getCarStatus().getCode())
                        .isMyCar(TrueFalse.getByBoolean(item.getUserId().equals(user.getId())).getCode())
                        .carTopic(item.getCarTopic())
                        .build())
                .collect(Collectors.toList());
    }

    private void enhanceOpt (UserBean user, List<CarSimpleVo> cars) {

        // 查询这些车下的乘客们(上车态的乘客)
        List<Long> carIds = cars.stream().map(CarSimpleVo::getId).collect(Collectors.toList());
        Map<Long, List<FtPassengerPo>> passengerMap = queryPassengerMap(carIds);

        // 司机的carId
        Long driverCarId = cars.stream()
                .filter(item -> item.getUserId().equals(user.getId()))
                .map(CarSimpleVo::getId)
                .findFirst().orElse(0L);

        // 乘客的carId
        Long passengerCarId = passengerMap.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .anyMatch(item -> item.getPassengerId().equals(user.getId())))
                .map(Map.Entry::getKey)
                .findFirst().orElse(0L);

        cars.forEach(item -> {
            // 当前用户为司机, 则仅有所属车的解散操作
            if (Utils.isPositive(driverCarId) && item.getId().equals(driverCarId)) {
                item.setCanDismiss(TrueFalse.TRUE.getCode());
            }
            // 当前用户为乘客，且不为司机，则仅有所在车的下车操作
            if (Utils.isPositive(passengerCarId) && !Utils.isPositive(driverCarId) && item.getId().equals(passengerCarId)) {
                item.setCanGetOff(TrueFalse.TRUE.getCode());
            }
            // 当前用户啥都不是，则有所有车的上车操作
            if (!Utils.isPositive(driverCarId) && !Utils.isPositive(passengerCarId)) {
                item.setCanGetOn(TrueFalse.TRUE.getCode());
            }
        });
    }

    private Map<Long, List<FtPassengerPo>> queryPassengerMap (List<Long> carIds) {

        if (CollectionUtils.isEmpty(carIds)) {
            return Collections.emptyMap();
        }

        List<FtPassengerPo> passengers = passengerMapper.selectList(Wrappers.lambdaQuery(FtPassengerPo.class)
                .in(FtPassengerPo::getCarId, carIds)
                .eq(FtPassengerPo::getPassengerStatus, PassengerStatus.GET_ON));

        if (CollectionUtils.isEmpty(passengers)) {
            return Collections.emptyMap();
        }

        return passengers.stream().collect(Collectors.groupingBy(FtPassengerPo::getCarId));
    }
}

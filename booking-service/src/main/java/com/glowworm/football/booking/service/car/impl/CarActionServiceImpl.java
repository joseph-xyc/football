package com.glowworm.football.booking.service.car.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glowworm.football.booking.dao.mapper.FtCarMapper;
import com.glowworm.football.booking.dao.mapper.FtPassengerMapper;
import com.glowworm.football.booking.dao.po.car.FtCarPo;
import com.glowworm.football.booking.dao.po.passenger.FtPassengerPo;
import com.glowworm.football.booking.domain.car.enums.CarStatus;
import com.glowworm.football.booking.domain.car.enums.CarType;
import com.glowworm.football.booking.domain.car.vo.GetOnFormVo;
import com.glowworm.football.booking.domain.car.vo.LaunchCarFormVo;
import com.glowworm.football.booking.domain.passenger.enums.BoardingWay;
import com.glowworm.football.booking.domain.passenger.enums.PassengerStatus;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.service.car.ICarActionService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-20 17:10
 */
@Slf4j
@Service
public class CarActionServiceImpl extends ServiceImpl<FtPassengerMapper, FtPassengerPo> implements ICarActionService {

    @Autowired
    private FtCarMapper carMapper;
    @Autowired
    private FtPassengerMapper passengerMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void launch(UserBean user, LaunchCarFormVo launchCarFormVo) {

        saveLaunch(user, launchCarFormVo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void getOn(UserBean user, GetOnFormVo getOnFormVo) {

        validGetOn(user, getOnFormVo);
        savePassenger(user, getOnFormVo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void getOff(UserBean user, Long carId) {

        FtPassengerPo passengerPo = FtPassengerPo.builder()
                .passengerStatus(PassengerStatus.GET_ON)
                .build();

        passengerMapper.update(passengerPo, Wrappers.lambdaQuery(FtPassengerPo.class)
                .eq(FtPassengerPo::getCarId, carId)
                .eq(FtPassengerPo::getPassengerId, user.getId()));
    }

    private void validGetOn (UserBean user, GetOnFormVo getOnFormVo) {

        Long carId = getOnFormVo.getCarId();
        FtCarPo carPo = carMapper.selectById(carId);
        Utils.isTrue(Objects.nonNull(carPo), "此车不存在");

        // 校验车状态
        Utils.throwError(carPo.getCarStatus().equals(CarStatus.ORDERED), "此车已上路, 无法上车");
        Utils.throwError(carPo.getCarStatus().equals(CarStatus.CANCEL), "此车已取消, 无法上车");
        Utils.throwError(carPo.getCarStatus().equals(CarStatus.EXPIRE), "此车已过期, 无法上车");

        // 乘客校验
        boolean exists = passengerMapper.exists(Wrappers.lambdaQuery(FtPassengerPo.class)
                .eq(FtPassengerPo::getPassengerId, user.getId())
                .eq(FtPassengerPo::getPassengerStatus, PassengerStatus.GET_ON));

        Utils.throwError(exists, "您已在其他车上, 无法重复上车");

    }

    private void savePassenger(UserBean user, GetOnFormVo getOnFormVo) {

        FtPassengerPo passengerPo = FtPassengerPo.builder()
                .carId(getOnFormVo.getCarId())
                .passengerId(user.getId())
                .boardingWay(BoardingWay.getByCode(getOnFormVo.getBoardingWay()))
                .passengerStatus(PassengerStatus.GET_ON)
                .build();

        saveOrUpdate(passengerPo);
    }

    private void saveLaunch (UserBean user, LaunchCarFormVo formVo) {

        FtCarPo carPo = FtCarPo.builder()
                .stadiumId(formVo.getStadiumId())
                .blockId(formVo.getBlockId())
                .scheduleId(formVo.getScheduleId())
                .userId(user.getId())
                .teamId(formVo.getTeamId())
                .carName(formVo.getCarName())
                .carTopic(formVo.getCarTopic())
                .recruitNum(formVo.getRecruitNum())
                .carType(CarType.getByCode(formVo.getCarType()))
                .carStatus(CarStatus.WAITING)
                .build();

        carMapper.insert(carPo);

        // 保存司机自己
        FtPassengerPo passengerPo = FtPassengerPo.builder()
                .carId(carPo.getId())
                .passengerId(user.getId())
                .boardingWay(BoardingWay.SELF)
                .passengerStatus(PassengerStatus.GET_ON)
                .build();

        passengerMapper.insert(passengerPo);
    }
}

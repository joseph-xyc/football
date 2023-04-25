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
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        validLaunch(user, launchCarFormVo);

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

    @Override
    public void dismiss(UserBean user, Long carId) {

        // 校验
        FtCarPo car = carMapper.selectById(carId);
        Utils.isTrue(Objects.nonNull(car), "不能存在的车");
        Utils.isTrue(car.getUserId().equals(user.getId()), "您不是此车的司机");
        Utils.throwError(car.getCarStatus().equals(CarStatus.ORDERED), "此车已成单, 无法解散");

        // 更新车状态
        carMapper.updateById(FtCarPo.builder()
                .id(carId)
                .carStatus(CarStatus.CANCEL)
                .build());

        // 所有人下车
        FtPassengerPo passengerPo = FtPassengerPo.builder()
                .passengerStatus(PassengerStatus.INVALID)
                .build();
        passengerMapper.update(passengerPo, Wrappers.lambdaQuery(FtPassengerPo.class)
                .eq(FtPassengerPo::getCarId, carId));
    }

    private void validGetOn (UserBean user, GetOnFormVo getOnFormVo) {

        Long carId = getOnFormVo.getCarId();
        FtCarPo carPo = carMapper.selectById(carId);
        Utils.isTrue(Objects.nonNull(carPo), "此车不存在");

        // 校验车状态
        Utils.throwError(carPo.getCarStatus().equals(CarStatus.ORDERED), "此车已上路, 无法上车");
        Utils.throwError(carPo.getCarStatus().equals(CarStatus.CANCEL), "此车已取消, 无法上车");
        Utils.throwError(carPo.getCarStatus().equals(CarStatus.EXPIRE), "此车已过期, 无法上车");
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

    private void validLaunch (UserBean user, LaunchCarFormVo formVo) {

        // 校验
        List<FtPassengerPo> passengerPoList = passengerMapper.selectList(Wrappers.lambdaQuery(FtPassengerPo.class)
                .eq(FtPassengerPo::getPassengerId, user.getId())
                .eq(FtPassengerPo::getPassengerStatus, PassengerStatus.GET_ON));

        if (CollectionUtils.isEmpty(passengerPoList)) {
            return;
        }

        // 此乘客所有在车上的list
        List<Long> carIds = passengerPoList.stream().map(FtPassengerPo::getCarId).collect(Collectors.toList());
        List<FtCarPo> cars = carMapper.selectBatchIds(carIds);

        boolean isPassenger = cars.stream()
                .map(FtCarPo::getScheduleId)
                .anyMatch(scheduleId -> scheduleId.equals(formVo.getScheduleId()));

        Utils.throwError(isPassenger, "您已经在此球场上，上了其他车, 请先下车或解散");
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

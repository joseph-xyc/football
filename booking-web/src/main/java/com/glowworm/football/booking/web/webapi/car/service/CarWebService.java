package com.glowworm.football.booking.web.webapi.car.service;

import com.glowworm.football.booking.dao.po.car.FtCarPo;
import com.glowworm.football.booking.domain.car.vo.CarSimpleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-04-20 17:41
 */
@Slf4j
@Service
public class CarWebService {

    public List<CarSimpleVo> po2vo (List<FtCarPo> carPos) {

        if (CollectionUtils.isEmpty(carPos)) {
            return Collections.emptyList();
        }

        return carPos.stream().map(item -> CarSimpleVo.builder()
                .id(item.getId())
                .scheduleId(item.getScheduleId())
                .carName(item.getCarName())
                .carType(item.getCarType().getCode())
                .build())
                .collect(Collectors.toList());
    }
}

package com.glowworm.football.booking.domain.car.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-20 16:48
 * 车的陈列vo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSimpleVo {

    private Long id;

    private Long scheduleId;

    private Long userId;

    private String carName;

    private String carTopic;

    private Integer carType;

    private Integer carStatus;

    private Integer isInTheCar;

    private Integer canDismiss;

    private Integer canGetOn;

    private Integer canGetOff;
}

package com.glowworm.football.booking.domain.car.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-20 16:52
 * 发车表单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaunchCarFormVo {

    private Long stadiumId;

    private Long blockId;

    private Long scheduleId;

    private String carName;

    private String carTopic;

    private Long teamId;

    private Integer recruitNum;

    private Integer carType;

    private List<Long> passengers;

}

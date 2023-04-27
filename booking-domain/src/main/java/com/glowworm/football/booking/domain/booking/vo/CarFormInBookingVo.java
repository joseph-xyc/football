package com.glowworm.football.booking.domain.booking.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-26 16:38
 * 预约表单中的车表单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarFormInBookingVo {

    private String carName;

    private String carTopic;

    private Integer recruitNum;
}

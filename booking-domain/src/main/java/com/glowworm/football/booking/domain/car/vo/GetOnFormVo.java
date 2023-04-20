package com.glowworm.football.booking.domain.car.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyongchang
 * @date 2023-04-20 19:48
 * 上车表单模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOnFormVo {

    private Long carId;

    private Integer boardingWay;

}

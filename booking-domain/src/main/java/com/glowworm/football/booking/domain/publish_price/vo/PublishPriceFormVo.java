package com.glowworm.football.booking.domain.publish_price.vo;

import com.glowworm.football.booking.domain.publish_price.enums.Week;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author xuyongchang
 * @date 2023-05-08 14:37
 * 刊例价初始化表单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishPriceFormVo {

    private Long stadiumId;

    private Long blockId;

    private Map<Week, List<PublishPriceItemVo>> priceMap;
}

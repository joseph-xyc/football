package com.glowworm.football.booking.domain.stadium.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-11 17:54
 * 球场详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StadiumInfoVo {

    private Long id;

    private String stadiumName;

    private String address;

    private Integer district;

    private String contactPerson;

    private String contactPhone;

    /**
     * 球场内的场地list
     */
    private List<StadiumBlockVo> blockList;

    /**
     * 球场图片
     */
    private List<String> images;

}

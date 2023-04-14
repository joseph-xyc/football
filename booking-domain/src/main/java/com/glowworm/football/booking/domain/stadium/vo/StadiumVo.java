package com.glowworm.football.booking.domain.stadium.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-10 11:44
 * 球场模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StadiumVo {

    private Long id;

    private String stadiumName;

    private String address;

    private Integer district;

    private Integer stadiumStatus;

    private String mainImageUrl;

    /**
     * 草皮类型
     */
    private List<String> swardTypeList;

    /**
     * 场地规格
     */
    private List<String> scaleTypeList;
}

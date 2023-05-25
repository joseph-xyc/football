package com.glowworm.football.booking.domain.stadium.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    private String introduce;

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

    /**
     * 是否收藏
     */
    private Integer isCollect;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 维度
     */
    private BigDecimal lat;

    /**
     * 经度
     */
    private BigDecimal lon;
}

package com.glowworm.football.booking.domain.stadium.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-08 15:13
 */
@Data
@Builder
public class QueryStadium {

    private Long id;

    private Long blockId;

    private String stadiumName;

    /**
     * StadiumSort
     */
    private Integer sort;

    /**
     * 用户维度
     */
    private Double userLat;

    /**
     * 用户经度
     */
    private Double userLon;

    /**
     * District
     */
    private List<Integer> district;

    /**
     * ScaleType
     */
    private List<Integer> scale;
}

package com.glowworm.football.booking.domain.stadium;

import com.glowworm.football.booking.domain.stadium.enums.StadiumStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:22
 * 球场
 */
@Data
@Builder
public class StadiumBean {

    private Long id;

    private String stadiumName;

    private String address;

    private Integer district;

    private Integer contactPerson;

    private Integer contactPhone;

    private StadiumStatus stadiumStatus;

    /**
     * 球场内的场地list
     */
    private List<StadiumBlockBean> blockList;
}

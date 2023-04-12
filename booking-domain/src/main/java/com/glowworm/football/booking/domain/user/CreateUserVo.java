package com.glowworm.football.booking.domain.user;

import lombok.Data;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Data
public class CreateUserVo {

    private String username;

    private String avatar;

    private Integer sex;
}

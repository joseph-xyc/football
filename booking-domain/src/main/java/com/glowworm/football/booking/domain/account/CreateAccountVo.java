package com.glowworm.football.booking.domain.account;

import lombok.Data;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Data
public class CreateAccountVo {

    private String username;

    private String avatar;

    private Integer sex;
}

package com.glowworm.football.booking.domain.user.vo;

import com.glowworm.football.booking.domain.user.enums.Position;
import com.glowworm.football.booking.domain.user.enums.Sex;
import com.glowworm.football.booking.domain.user.enums.Style;
import lombok.Data;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Data
public class CreateUserFormVo {

    private String username;

    private String nickname;

    private String avatar;

    private Sex sex;

    private List<Position> pos;

    private List<Style> styles;
}

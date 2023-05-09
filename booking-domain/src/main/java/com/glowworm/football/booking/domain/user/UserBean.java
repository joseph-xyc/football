package com.glowworm.football.booking.domain.user;

import com.glowworm.football.booking.domain.user.enums.*;
import lombok.*;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {

    private Long id;

    private String openId;

    private String username;

    private String nickname;

    private UserType userType;

    private UserLevel level;

    private String avatar;

    private Sex sex;

    private List<Position> pos;

    private List<Style> styles;

    private Integer likes;

    private Integer userStatus;

    public static UserBean SYS = UserBean.builder()
            .id(-888L)
            .openId("-888")
            .username(UserType.SYS.getDesc())
            .userType(UserType.SYS)
            .build();
}

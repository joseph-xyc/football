package com.glowworm.football.booking.domain.user;

import com.glowworm.football.booking.domain.user.enums.UserType;
import lombok.*;

import java.sql.Timestamp;

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

    private String username;

    private String openId;

    private String sourceFrom;

    private Integer userStatus;

    private UserType userType;

    private String avatar;

    private Integer sex;

    private Timestamp ctime;

    private Timestamp mtime;

    public static UserBean SYS = UserBean.builder()
            .id(-888L)
            .openId("-888")
            .username(UserType.SYS.getDesc())
            .userType(UserType.SYS)
            .build();
}

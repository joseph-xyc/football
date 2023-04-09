package com.glowworm.football.booking.domain.account;

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
public class AccountBean {

    private Long id;

    private String username;

    private String openId;

    private String sourceFrom;

    private Integer accountStatus;

    private String avatar;

    private Integer sex;

    private Timestamp ctime;

    private Timestamp mtime;
}

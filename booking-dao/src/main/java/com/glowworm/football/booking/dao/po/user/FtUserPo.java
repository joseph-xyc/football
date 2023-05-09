package com.glowworm.football.booking.dao.po.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.user.enums.Sex;
import com.glowworm.football.booking.domain.user.enums.UserLevel;
import com.glowworm.football.booking.domain.user.enums.UserType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Data
@Builder
@TableName("ft_user")
public class FtUserPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String openId;

    private String username;

    private String nickname;

    private UserType userType;

    private UserLevel level;

    private String avatar;

    private Sex sex;

    private String position;

    private String style;

    private Integer likes;

    private Integer userStatus;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

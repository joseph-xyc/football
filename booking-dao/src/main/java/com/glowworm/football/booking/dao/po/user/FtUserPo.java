package com.glowworm.football.booking.dao.po.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private String username;

    private String openId;

    private String sourceFrom;

    private Integer userStatus;

    private UserType userType;

    private String avatar;

    private Integer sex;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

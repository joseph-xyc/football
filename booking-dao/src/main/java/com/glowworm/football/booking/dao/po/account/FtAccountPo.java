package com.glowworm.football.booking.dao.po.account;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Data
@Builder
@TableName("ft_account")
public class FtAccountPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String openId;

    private String sourceFrom;

    private Integer accountStatus;

    private String avatar;

    private Integer sex;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

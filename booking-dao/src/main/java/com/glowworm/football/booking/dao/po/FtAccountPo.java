package com.glowworm.football.booking.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Data
@SuperBuilder
@NoArgsConstructor
@TableName("ft_account")
public class FtAccountPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

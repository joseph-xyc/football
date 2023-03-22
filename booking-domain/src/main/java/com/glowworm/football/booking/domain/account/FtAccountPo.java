package com.glowworm.football.booking.domain.account;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Data
@Repository
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

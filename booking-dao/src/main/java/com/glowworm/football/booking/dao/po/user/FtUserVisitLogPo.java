package com.glowworm.football.booking.dao.po.user;

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
 * @date 2023-04-01 16:21
 */
@Data
@SuperBuilder
@NoArgsConstructor
@TableName("ft_user_visit_log")
public class FtUserVisitLogPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String openId;

    private Integer count;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

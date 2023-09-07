package com.glowworm.football.booking.dao.po.msg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.msg.enums.MsgBizType;
import com.glowworm.football.booking.domain.msg.enums.MsgSysType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-09-06 17:19
 */
@Data
@Builder
@TableName("ft_msg")
public class FtMsgPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private MsgSysType msgSysType;

    private MsgBizType msgBizType;

    private Integer isRead;

    private Timestamp date;

    private String content;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

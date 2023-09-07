package com.glowworm.football.booking.domain.msg.vo;

import com.glowworm.football.booking.domain.msg.enums.MsgBizType;
import com.glowworm.football.booking.domain.msg.enums.MsgSysType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-09-07 10:56
 * 消息vo模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgVo {

    private Long id;

    private Long userId;

    private MsgSysType msgSysType;

    private MsgBizType msgBizType;

    private Integer isRead;

    private Timestamp date;

    private String content;

}

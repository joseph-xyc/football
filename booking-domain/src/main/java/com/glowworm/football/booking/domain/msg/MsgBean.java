package com.glowworm.football.booking.domain.msg;

import com.glowworm.football.booking.domain.msg.enums.MsgBizType;
import com.glowworm.football.booking.domain.msg.enums.MsgSysType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-09-06 19:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgBean {

    private Long userId;

    private MsgSysType msgSysType;

    private MsgBizType msgBizType;

    private Timestamp date;

    private String content;
}

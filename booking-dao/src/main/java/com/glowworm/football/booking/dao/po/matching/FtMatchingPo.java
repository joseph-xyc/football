package com.glowworm.football.booking.dao.po.matching;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.matching.enums.MatchingStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-07-20 20:38
 */
@Data
@Builder
@TableName("ft_matching")
public class FtMatchingPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Long scheduleId;

    private Long userId;

    private Timestamp matchingTime;

    private MatchingStatus matchingStatus;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

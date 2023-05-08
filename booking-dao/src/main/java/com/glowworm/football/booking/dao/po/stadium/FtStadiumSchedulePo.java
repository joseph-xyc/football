package com.glowworm.football.booking.dao.po.stadium;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:00
 */
@Data
@Builder
@TableName("ft_stadium_schedule")
public class FtStadiumSchedulePo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Timestamp date;

    private ScheduleClock clockBegin;

    private ScheduleClock clockEnd;

    private ScheduleStatus status;

    private Integer price;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

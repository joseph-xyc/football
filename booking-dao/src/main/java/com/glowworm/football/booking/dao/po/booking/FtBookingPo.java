package com.glowworm.football.booking.dao.po.booking;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.booking.enums.BookingStatus;
import com.glowworm.football.booking.domain.booking.enums.BookingType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-17 16:43
 * 预约表
 */
@Data
@Builder
@TableName("ft_booking")
public class FtBookingPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Long scheduleId;

    private Long userId;

    private Long teamId;

    private BookingType bookingType;

    private BookingStatus bookingStatus;

    private Integer price;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

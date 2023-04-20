package com.glowworm.football.booking.dao.po.passenger;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.passenger.enums.BoardingWay;
import com.glowworm.football.booking.domain.passenger.enums.PassengerStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-20 19:29
 */
@Data
@Builder
@TableName("ft_passenger")
public class FtPassengerPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long carId;

    private Long passengerId;

    private BoardingWay boardingWay;

    private PassengerStatus passengerStatus;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

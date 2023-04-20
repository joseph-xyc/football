package com.glowworm.football.booking.dao.po.car;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.car.enums.CarStatus;
import com.glowworm.football.booking.domain.car.enums.CarType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-20 16:27
 * 发车表
 */
@Data
@Builder
@TableName("ft_car")
public class FtCarPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Long scheduleId;

    private String carName;

    private Long userId;

    private Long teamId;

    private Integer recruitNum;

    private CarType carType;

    private CarStatus carStatus;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

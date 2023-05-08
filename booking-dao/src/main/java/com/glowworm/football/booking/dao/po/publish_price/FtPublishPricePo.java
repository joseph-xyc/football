package com.glowworm.football.booking.dao.po.publish_price;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.publish_price.enums.Week;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-05-08 11:40
 */
@Data
@Builder
@TableName("ft_publish_price")
public class FtPublishPricePo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private Long blockId;

    private Week week;

    private ScheduleClock clockBegin;

    private ScheduleClock clockEnd;

    private Integer price;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

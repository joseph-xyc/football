package com.glowworm.football.booking.dao.po.stadium;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.stadium.enums.ScaleType;
import com.glowworm.football.booking.domain.stadium.enums.StadiumBlockStatus;
import com.glowworm.football.booking.domain.stadium.enums.SwardType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-08 13:49
 * 球场-场地
 */
@Data
@Builder
@TableName("ft_stadium_block")
public class FtStadiumBlockPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private String blockName;

    private ScaleType scaleType;

    private SwardType swardType;

    private StadiumBlockStatus blockStatus;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

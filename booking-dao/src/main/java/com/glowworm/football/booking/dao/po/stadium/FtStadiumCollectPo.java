package com.glowworm.football.booking.dao.po.stadium;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.stadium.enums.StadiumCollectStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-05-23 14:53
 */
@Data
@Builder
@TableName("ft_stadium_collect")
public class FtStadiumCollectPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private Long userId;

    private StadiumCollectStatus collectStatus;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

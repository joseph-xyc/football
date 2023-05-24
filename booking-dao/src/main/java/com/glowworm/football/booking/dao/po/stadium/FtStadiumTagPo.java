package com.glowworm.football.booking.dao.po.stadium;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-05-24 15:16
 */
@Data
@Builder
@TableName("ft_stadium_tag")
public class FtStadiumTagPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;

    private String tag;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

package com.glowworm.football.booking.dao.po.stadium;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.stadium.enums.StadiumImageType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-10 21:25
 * 球场图片标
 */
@Data
@Builder
@TableName("ft_stadium_image")
public class FtStadiumImagePo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long stadiumId;
    private String url;

    private StadiumImageType imageType;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;
}

package com.glowworm.football.booking.dao.po.stadium;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.glowworm.football.booking.domain.stadium.enums.StadiumStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-04-08 13:41
 * 球场表
 */
@Data
@Builder
@TableName("ft_stadium")
public class FtStadiumPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String stadiumName;

    private String address;

    private Integer district;

    private String contactPerson;

    private Integer contactPhone;

    private StadiumStatus stadiumStatus;

    @TableLogic
    private Integer isDeleted;

    private Timestamp ctime;

    private Timestamp mtime;

}

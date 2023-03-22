package com.glowworm.football.booking.domain.test;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author xuyongchang
 * @date 2023/3/17
 */
@Data
@TableName("cm_label")
public class CmLabel {

    @TableId
    private Integer id;

    @TableField("label_name")
    private String labelName;
}

package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-06-06 20:14
 * 行政区划
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum District {

    HP(1, "黄埔"),

    XH(2, "徐汇"),

    CN(3, "长宁"),

    JA(4, "静安"),

    PT(5, "普陀"),

    HK(6, "虹口"),

    YP(7, "杨浦"),

    BS(8, "宝山"),

    MH(9, "闵行"),

    JD(10, "嘉定"),

    PD(11, "浦东"),

    SJ(12, "松江"),

    JS(13, "金山"),

    QP(14, "青浦"),

    FX(15, "奉贤"),

    CM(16, "崇明"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}

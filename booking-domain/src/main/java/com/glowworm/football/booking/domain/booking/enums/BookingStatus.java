package com.glowworm.football.booking.domain.booking.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuyongchang
 * @date 2023-04-17 16:54
 * 预约状态
 */
@Getter
@AllArgsConstructor
public enum BookingStatus {

    PLANNING(0, "组队中", "组队中"),

    WAIT_STADIUM_CONFIRM(1, "待球场确认", "组队完成，等待球场确认"),

    BOOKED(10, "预订成功", "预订成功"),

    PERSONAL_CANCEL(50, "个人取消", "取消预约"),

    STADIUM_CANCEL(51, "球场撤销", "场地方取消"),

    SYS_CANCEL(59, "系统撤销", "系统取消"),



    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    private final String brief;
}

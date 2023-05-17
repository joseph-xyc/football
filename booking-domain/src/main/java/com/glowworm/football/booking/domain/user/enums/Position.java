package com.glowworm.football.booking.domain.user.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-09 16:57
 * 位置
 */
@Getter
@AllArgsConstructor
public enum Position {

    GK(0, "门将"),

    CB(1, "中后卫"),

    RB(2, "右后卫"),

    LB(3, "左后卫"),

    CDM(20, "后腰"),

    CM(21, "中场"),

    CAM(22, "前腰"),

    CF(30, "中锋"),

    RF(31, "右前锋"),

    LF(32, "左前锋"),

    ;

    private final Integer code;

    private final String desc;

    public static List<Position> getPositions (String str) {

        if (Strings.isEmpty(str)) {
            return Lists.newArrayList();
        }

        List<String> codes = Arrays.stream(str.split(",")).collect(Collectors.toList());

        return Arrays.stream(values())
                .filter(item -> codes.contains(String.valueOf(item.getCode())))
                .collect(Collectors.toList());
    }

    public static String getStr (List<Position> pos) {

        if (CollectionUtils.isEmpty(pos)) {
            return Strings.EMPTY;
        }

        return pos.stream().map(Position::getCode).map(String::valueOf).collect(Collectors.joining(","));
    }
}

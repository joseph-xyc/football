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
 * @date 2023-05-09 17:02
 * 风格
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Style {

    S_1(1, "瞎J8踢"),

    S_2(2, "推土机"),

    S_3(3, "技术流"),

    S_4(4, "作风硬朗"),

    S_5(5, "拼抢积极"),

    S_6(6, "球王踢法"),

    S_7(7, "散步养生"),

    ;

    private final Integer code;

    private final String desc;

    public static List<Style> getStyles (String str) {

        if (Strings.isEmpty(str)) {
            return Lists.newArrayList();
        }

        List<String> codes = Arrays.stream(str.split(",")).collect(Collectors.toList());

        return Arrays.stream(values())
                .filter(item -> codes.contains(String.valueOf(item.getCode())))
                .collect(Collectors.toList());
    }

    public static String getStr (List<Style> styles) {

        if (CollectionUtils.isEmpty(styles)) {
            return Strings.EMPTY;
        }

        return styles.stream().map(Style::getCode).map(String::valueOf).collect(Collectors.joining(","));
    }
}

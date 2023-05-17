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
public enum Style {

    S_1(1, "出汗养生"),

    S_2(2, "快乐足球"),

    S_3(3, "团队配合"),

    S_4(4, "直接暴趟"),

    S_5(5, "技术细腻"),

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

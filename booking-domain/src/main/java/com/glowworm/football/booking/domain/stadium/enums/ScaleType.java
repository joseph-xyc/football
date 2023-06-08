package com.glowworm.football.booking.domain.stadium.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-04-10 11:54
 */
@Getter
@AllArgsConstructor
public enum ScaleType {

    C_5(0, "5人场", "5"),

    C_7(1, "7人场", "7"),

    C_8(2, "8人场", "8"),

    C_11(3, "11人场", "11"),

    X_LARGE(4, "大操场", "综"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    private final String abbr;

    public static ScaleType get (Integer code) {

        if (Objects.isNull(code)) {
            return null;
        }

        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static List<ScaleType> get (List<Integer> codes) {

        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptyList();
        }

        return Arrays.stream(values())
                .filter(item -> codes.contains(item.getCode()))
                .collect(Collectors.toList());
    }
}

package com.glowworm.football.booking.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Slf4j
public class Utils {

    public static boolean isPositive(Integer i) {
        return i != null && i.compareTo(0) > 0;
    }

    public static boolean isPositive(Long l) {
        return l != null && l.compareTo(0L) > 0;
    }

    public static boolean isPositive(Double d) {
        return d != null && d.compareTo(0.0) > 0;
    }

    public static boolean isPositive(BigDecimal b) {
        return b != null && b.compareTo(BigDecimal.ZERO) > 0;
    }


    public static <T, S> List<T> copy (List<S> source, Class<T> clazz) {

        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        return source.stream().map(item -> copy(item, clazz)).collect(Collectors.toList());
    }

    public static <T, S> T copy (S source, Class<T> clazz) {

        T t = null;

        try {
            if (Objects.isNull(source)) {
                return clazz.newInstance();
            }

            t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
        } catch (Exception e) {
            log.error("FtUtil copy error", e);
        }
        return t;
    }

}

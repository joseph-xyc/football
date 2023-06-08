package com.glowworm.football.booking.service.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.glowworm.football.booking.domain.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static boolean isPositive(BigDecimal b) {
        return b != null && b.compareTo(BigDecimal.ZERO) > 0;
    }

    public static <R> R isPositive(Integer i, R positive, R negative) {
        return isPositive(i) ? positive : negative;
    }

    public static <R> R isPositive(Long l, R positive, R negative) {
        return isPositive(l) ? positive : negative;
    }

    public static <R> R isPositive(BigDecimal b, R positive, R negative) {
        return isPositive(b) ? positive : negative;
    }

    public static void throwError (boolean expression, String message) {
        if (expression) {
            throw new ValidateException(message);
        }
    }

    public static void isTrue (boolean expression, String message) {
        if (!expression) {
            throw new ValidateException(message);
        }
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

    public static <T> void disableQuery (LambdaQueryWrapper<T> query) {

        // 强制使sql不生效
        query.apply("1 = 2");
    }

    public static BigDecimal divide (BigDecimal numerator, BigDecimal divisor) {

        return divide(numerator, divisor, 2);
    }


    public static BigDecimal divide (BigDecimal numerator, BigDecimal divisor, Integer scale) {

        if (divisor.compareTo(BigDecimal.ZERO) == 0 || divisor.compareTo(BigDecimal.valueOf(0.0)) == 0) {
            return BigDecimal.ZERO;
        }
        return numerator.divide(divisor, scale, RoundingMode.HALF_UP);
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     *
     * @param lon1 经度1
     * @param lat1 维度1
     * @param lon2 经度2
     * @param lat2 维度2
     * @return 返回2点之间的距离，单位m
     */
    public static double getDistance (double lon1,double lat1,double lon2, double lat2) {

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6371000;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

}

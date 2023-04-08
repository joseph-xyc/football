package com.glowworm.football.booking.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Slf4j
public class FtUtil {

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

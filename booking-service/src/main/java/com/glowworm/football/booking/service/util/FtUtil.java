package com.glowworm.football.booking.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Slf4j
public class FtUtil {

    public static <T, S> T copy(S source, Class<T> clazz) {

        if (Objects.isNull(source)) {
            return null;
        }

        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
        } catch (Exception e) {
            log.error("FtUtil copy error", e);
        }
        return t;
    }
}

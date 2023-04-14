package com.glowworm.football.booking.service.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xuyongchang
 * @date 2023-04-14 16:48
 */
public class MybatisUtils {

    public static void isTrue(boolean value, Runnable execute) {
        if (value) {
            execute.run();
        }
    }

    public static void isFalse(boolean value, Runnable execute) {
        if (!value) {
            execute.run();
        }
    }

    public static <T> void notNull(T value, Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public static <T, R> R notNull(T value, Function<T, R> fun) {
        if (value != null) {
            return fun.apply(value);
        }
        return null;
    }

    public static <T extends Collection<E>, E> void notEmpty(T value, Consumer<T> consumer) {
        if (notEmpty(value)) {
            consumer.accept(value);
        }
    }

    public static boolean notEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }
}

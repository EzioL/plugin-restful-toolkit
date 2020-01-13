package java.com.ezio.plugin.utils;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public final class Optionals {
    public Optionals() {
    }

    public static <T> Optional<T> ofPredicable(T value, Predicate<T> predicate) {
        return Optional.ofNullable(value).filter(predicate);
    }

    public static <T> Optional<T> withSupplier(T value, Predicate<T> predicate, Supplier<T> supplier) {
        return Optional.ofNullable(value).filter(predicate).map((e) -> supplier.get());
    }
}

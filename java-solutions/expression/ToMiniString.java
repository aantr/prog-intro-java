package expression;

import java.util.Objects;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }

    default boolean equals(ToMiniString other) {
        if (other == null) {
            return false;
        }
        return Objects.equals(toString(), other.toString());
    }

    int hashCode();
}

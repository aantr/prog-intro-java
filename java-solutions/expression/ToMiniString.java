package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }

    default boolean equals(ToMiniString toMiniString) {
        if (toMiniString == null) {
            return false;
        }
        return toString().equals(toMiniString.toString());
    }
}

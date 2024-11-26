package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }

    default boolean equals(ToMiniString toMiniString) {
        return toString().equals(toMiniString.toString());
    }
}

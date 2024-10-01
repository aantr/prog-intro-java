package wordStat;

import base.Named;
import base.Pair;
import base.Selector;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Tests for <a href="https://www.kgeorgiy.info/courses/prog-intro/homeworks.html#wordstat">Word Statistics</a> homework
 * of <a href="https://www.kgeorgiy.info/courses/prog-intro/">Introduction to Programming</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class WordStatTest {
    /// Base
    private static final Named<Function<String, Stream<String>>> ID  = Named.of("", Stream::of);
    private static final Named<Comparator<Pair<String, Integer>>> INPUT = Named.of("Input", Comparator.comparingInt(p -> 0));


    /// Words
    private static final Named<Comparator<Pair<String, Integer>>> WORDS =
            Named.of("Words", Comparator.comparing(Pair<String, Integer>::first).reversed());

    /// Suffix
    public static final int SIZE = 3;

    static Named<Function<String, Stream<String>>> length(
            final String name,
            final int length,
            final Function<String, Stream<String>> lng,
            final Function<String, Stream<String>> shrt
    ) {
        return Named.of(name, s -> (s.length() >= length ? lng : shrt).apply(s));
    }

    private static final Named<Function<String, Stream<String>>> SUFFIX =
            length("Suffix", SIZE, s -> Stream.of(s.substring(s.length() - SIZE)), Stream::of);


    /// Common
    public static final Selector SELECTOR = new Selector(WordStatTester.class)
            .variant("Base",            WordStatTester.variant(INPUT, ID))
            .variant("WordsSuffix",     WordStatTester.variant(WORDS, SUFFIX))
            ;

    private WordStatTest() {
        // Utility class
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}

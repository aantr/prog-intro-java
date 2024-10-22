package wspp;

import base.ExtendedRandom;
import base.Named;
import base.Pair;
import base.TestCounter;
import wordStat.WordStatChecker;
import wordStat.WordStatTester;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class WsppTester {
    private WsppTester() {
    }

    public static <T> Consumer<TestCounter> variant(
            final Named<Comparator<Map.Entry<String, Integer>>> comparator,
            final Named<IntFunction<IntStream>> selector,
            final Named<Extractor<T>> extractor,
            final Named<String> extra
    ) {
        // Stream "magic" code. You do not expect to understand it
        return counter -> WordStatChecker.test(
                counter,
                "Wspp" + comparator.getName() + selector.getName() + extractor.getName() + extra.getName(),
                text -> {
                    final Map<String, Integer> totals = Arrays.stream(text)
                            .flatMap(Arrays::stream)
                            .map(word -> word.toLowerCase(Locale.ROOT))
                            .collect(Collectors.toMap(Function.identity(), k -> 1, Integer::sum, LinkedHashMap::new));
                    final int[] lengths = Arrays.stream(text).mapToInt(a -> a.length).toArray();
                    final int[] sizes = new int[lengths.length];
                    for (int i = 0, start = 0; i < lengths.length; i++) {
                        sizes[i] = start;
                        start += lengths[i];
                    }

                    final Map<String, String> selected = IntStream.range(0, text.length).boxed()
                            .flatMap(r -> {
                                     final String[] line = text[r];
                                     return IntStream.range(0, line.length).boxed()
                                             .collect(Collectors.groupingBy(
                                                     w -> line[w].toLowerCase(Locale.ROOT),
                                                     Collectors.collectingAndThen(
                                                             Collectors.mapping(
                                                                     w -> extractor.getValue().select(
                                                                             r + 1,
                                                                             w + 1,
                                                                             line.length,
                                                                             sizes[r] + w + 1
                                                                     ),
                                                                     Collectors.toUnmodifiableList()
                                                             ),
                                                             list -> selector.getValue().apply(list.size()).mapToObj(list::get).collect(
                                                                     Collectors.toUnmodifiableList())
                                                     )
                                             ))
                                             .entrySet().stream();
                                 }
                            )
                            .collect(Collectors.groupingBy(
                                    Map.Entry::getKey,
                                    Collectors.flatMapping(
                                            e -> e.getValue().stream(),
                                            Collectors.mapping(
                                                    String::valueOf,
                                                    Collectors.mapping(" "::concat, Collectors.joining())
                                            )
                                    )
                            ));
                    return totals.entrySet().stream()
                            .sorted(comparator.getValue())
                            .map(e -> Pair.of(e.getKey(), e.getValue() + selected.get(e.getKey())))
                            .collect(Collectors.toList());
                },
                checker -> {
                    final Pattern pattern = Pattern.compile(new String(Base64.getDecoder().decode("W15ccHtJc0xldHRlcn0nXHB7UGR9" + extra.getValue()), StandardCharsets.US_ASCII) + "]+");
                    final String good = String.join("", pattern.split(WordStatTester.POST_LOWER));

                    checker.test(pattern, "To be, or not to be, that is the question:");
                    checker.test(
                            pattern,
                            "Monday's child is fair of face.",
                            "Tuesday's child is full of grace."
                    );
                    checker.test(
                            pattern,
                            "Шалтай-Болтай",
                            "Сидел на стене.",
                            "Шалтай-Болтай",
                            "Свалился во сне."
                    );

                    checker.randomTest(3, 10, 10, 3, ExtendedRandom.ENGLISH, WordStatChecker.SIMPLE_DELIMITERS);
                    checker.randomTest(10, 3, 5, 5, ExtendedRandom.RUSSIAN, WordStatChecker.SIMPLE_DELIMITERS);
                    checker.randomTest(3, 10, 10, 3, ExtendedRandom.GREEK, WordStatChecker.SIMPLE_DELIMITERS);
                    checker.randomTest(3, 10, 10, 3, WordStatChecker.DASH, WordStatChecker.SIMPLE_DELIMITERS);
                    checker.randomTest(3, 10, 10, 3, ExtendedRandom.ENGLISH, WordStatChecker.ADVANCED_DELIMITERS);
                    checker.randomTest(10, 3, 5, 5, ExtendedRandom.RUSSIAN, WordStatChecker.ADVANCED_DELIMITERS);
                    checker.randomTest(3, 10, 10, 3, ExtendedRandom.GREEK, WordStatChecker.ADVANCED_DELIMITERS);
                    checker.randomTest(3, 10, 10, 3, WordStatChecker.DASH, WordStatChecker.ADVANCED_DELIMITERS);

                    checker.randomTest(10, 20, 10, 3, good, WordStatChecker.SIMPLE_DELIMITERS);
                    checker.randomTest(10, 20, 10, 3, good, WordStatChecker.ADVANCED_DELIMITERS);

                    final int d = TestCounter.DENOMINATOR;
                    final int d2 = TestCounter.DENOMINATOR2;
                    checker.randomTest(100, 1000 / d, 1000 / d2, 1000 / d2, WordStatChecker.ALL, WordStatChecker.ADVANCED_DELIMITERS);
                    checker.randomTest(10, 1000 / d, 1000 / d2, 1000 / d2, good, WordStatChecker.ADVANCED_DELIMITERS);

                    checker.randomTest(10000 / d, 20, 10, 5, WordStatChecker.ALL, WordStatChecker.ADVANCED_DELIMITERS);
                    checker.randomTest(1000000 / d, 2, 2, 1, WordStatChecker.ALL, WordStatChecker.ADVANCED_DELIMITERS);

                    checker.test(pattern, WordStatTester.PRE_LOWER);
                    checker.test(pattern, WordStatTester.POST_LOWER);
                }
        );
    }

    @FunctionalInterface
    public interface Extractor<T> {
        T select(int l, int ws, int wt, int g);
    }
}

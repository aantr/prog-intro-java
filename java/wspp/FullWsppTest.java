package wspp;

import base.Named;
import base.Selector;

import java.util.Comparator;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class FullWsppTest {
    private static final Named<Comparator<Map.Entry<String, Integer>>> INPUT = Named.of("", Comparator.comparingInt(e -> 0));
    private static final Named<Comparator<Map.Entry<String, Integer>>> COUNT = Named.of("Count", Comparator.comparingInt(Map.Entry::getValue));
    private static final Named<Comparator<Map.Entry<String, Integer>>> SORTED = Named.of("Sorted", Map.Entry.comparingByKey());
    private static final Named<Comparator<Map.Entry<String, Integer>>> SORTED__R = Named.of("SortedR", Comparator.comparing(e -> reverse(e.getKey())));

    private static String reverse(final String string) {
        return new StringBuilder(string).reverse().toString();
    }

    private static final Named<IntFunction<IntStream>> ALL = Named.of("", size -> IntStream.range(0, size));
    private static final Named<IntFunction<IntStream>> FIRST = Named.of("First", size -> IntStream.of(0));
    private static final Named<IntFunction<IntStream>> EVEN = Named.of("Even", size -> IntStream.range(0, size).filter(i -> (i & 1) == 1));
    private static final Named<IntFunction<IntStream>> LAST = Named.of("Last", size -> IntStream.of(size - 1));

    private static final Named<WsppTester.Extractor<Object>> WSPP = Named.of("", (r, w, t, g) -> g);
    private static final Named<WsppTester.Extractor<Integer>> LOCAL = Named.of("L", (r, w, t, g) -> w);
    private static final Named<WsppTester.Extractor<Integer>> GLOBAL = Named.of("G", (r, w, t, g) -> g);
    private static final Named<WsppTester.Extractor<String>> POSITION__B = Named.of("PositionB", (r, w, t, g) -> r + ":" + w);
    private static final Named<WsppTester.Extractor<String>> POSITION__E = Named.of("PositionE", (r, w, t, g) -> r + ":" + (t - w + 1));

    private static final Named<String> NONE = Named.of("", "");
    private static final Named<String> DIGITS = Named.of("Digits", "XHB7TmR9");
    private static final Named<String> JAVA = Named.of("Java", "XHB7TmR9JF8=");
    private static final Named<String> CURRENCY = Named.of("Currency", "XHB7U2N9");

    public static final Selector SELECTOR = new Selector(WsppTester.class)
            .variant("Base",                WsppTester.variant(INPUT,       ALL,    WSPP,   NONE))

            .variant("Digits",              WsppTester.variant(INPUT,       ALL,    WSPP,   DIGITS))
            .variant("Java",                WsppTester.variant(INPUT,       ALL,    WSPP,   JAVA))
            .variant("Currency",            WsppTester.variant(INPUT,       ALL,    WSPP,   CURRENCY))

            .variant("First__G",            WsppTester.variant(INPUT,       FIRST,  GLOBAL, NONE))
            .variant("First__L",            WsppTester.variant(INPUT,       FIRST,  LOCAL,  NONE))
            .variant("Even__G",             WsppTester.variant(INPUT,       EVEN,   GLOBAL, NONE))
            .variant("Even__L",             WsppTester.variant(INPUT,       EVEN,   LOCAL,  NONE))
            .variant("Last__G",             WsppTester.variant(INPUT,       LAST,   GLOBAL, NONE))
            .variant("Last__L",             WsppTester.variant(INPUT,       LAST,   LOCAL,  NONE))

            .variant("CountFirst__G",       WsppTester.variant(COUNT,       FIRST,  GLOBAL, NONE))
            .variant("CountFirst__L",       WsppTester.variant(COUNT,       FIRST,  LOCAL,  NONE))
            .variant("CountEven__G",        WsppTester.variant(COUNT,       EVEN,   GLOBAL, NONE))
            .variant("CountEven__L",        WsppTester.variant(COUNT,       EVEN,   LOCAL,  NONE))
            .variant("CountLast__G",        WsppTester.variant(COUNT,       LAST,   GLOBAL, NONE))
            .variant("CountLast__L",        WsppTester.variant(COUNT,       LAST,   LOCAL,  NONE))

            .variant("SortedFirst__G",      WsppTester.variant(SORTED,      FIRST,  GLOBAL, NONE))
            .variant("SortedFirst__L",      WsppTester.variant(SORTED,      FIRST,  LOCAL,  NONE))
            .variant("SortedEven__G",       WsppTester.variant(SORTED,      EVEN,   GLOBAL, NONE))
            .variant("SortedEven__L",       WsppTester.variant(SORTED,      EVEN,   LOCAL,  NONE))
            .variant("SortedLast__G",       WsppTester.variant(SORTED,      LAST,   GLOBAL, NONE))
            .variant("SortedLast__L",       WsppTester.variant(SORTED,      LAST,   LOCAL,  NONE))

            .variant("SortedFirst__RG",     WsppTester.variant(SORTED__R,   FIRST,  GLOBAL, NONE))
            .variant("SortedFirst__RL",     WsppTester.variant(SORTED__R,   FIRST,  LOCAL,  NONE))
            .variant("SortedEven__RG",      WsppTester.variant(SORTED__R,   EVEN,   GLOBAL, NONE))
            .variant("SortedEven__RL",      WsppTester.variant(SORTED__R,   EVEN,   LOCAL,  NONE))
            .variant("SortedLast__RG",      WsppTester.variant(SORTED__R,   LAST,   GLOBAL, NONE))
            .variant("SortedLast__RL",      WsppTester.variant(SORTED__R,   LAST,   LOCAL,  NONE))

            .variant("Position__B",         WsppTester.variant(INPUT,       ALL,    POSITION__B,   NONE))
            .variant("CountPosition__B",    WsppTester.variant(COUNT,       ALL,    POSITION__B,   NONE))
            .variant("SortedPosition__B",   WsppTester.variant(SORTED,      ALL,    POSITION__B,   NONE))
            .variant("SortedPosition__BR",  WsppTester.variant(SORTED__R,   ALL,    POSITION__B,   NONE))
            .variant("Position__E",         WsppTester.variant(INPUT,       ALL,    POSITION__E,   NONE))
            .variant("CountPosition__E",    WsppTester.variant(COUNT,       ALL,    POSITION__E,   NONE))
            .variant("SortedPosition__E",   WsppTester.variant(SORTED,      ALL,    POSITION__E,   NONE))
            .variant("SortedPosition__ER",  WsppTester.variant(SORTED__R,   ALL,    POSITION__B,   NONE))
            ;

    private FullWsppTest() {
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}

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
public final class WsppTest {
    /// Base
    private static final Named<Comparator<Map.Entry<String, Integer>>> INPUT = Named.of("", Comparator.comparingInt(e -> 0));
    private static final Named<IntFunction<IntStream>> ALL = Named.of("", size -> IntStream.range(0, size));
    private static final Named<WsppTester.Extractor<Object>> WSPP = Named.of("", (r, w, t, g) -> g);
    private static final Named<String> NONE = Named.of("", "");


    public static final Selector SELECTOR = new Selector(WsppTester.class)
            .variant("Base",            WsppTester.variant(INPUT, ALL, WSPP, NONE))
            ;

    private WsppTest() {
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}

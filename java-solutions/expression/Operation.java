package expression;

import java.util.Map;

public abstract class Operation extends BaseExpression {
    protected final BaseExpression f, s;
    private final char symbol;

    public Operation(final BaseExpression f, final BaseExpression s, final char symbol) {
        this.f = f;
        this.s = s;
        this.symbol = symbol;
    }

    public abstract int operation(final int a, final int b);

    public abstract float operation(final float a, final float b);

    @Override
    public int evaluate(final int x) {
        return operation(f.evaluate(x), s.evaluate(x));
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return operation(f.evaluate(x, y, z), s.evaluate(x, y, z));
    }

    @Override
    public float evaluateF(final Map<String, Float> variables) {
        return operation(f.evaluateF(variables), s.evaluateF(variables));
    }

    @Override
    public String toString() {
        return "(%s %c %s)".formatted(f, symbol, s);
    }

    private String insertBraces(final String str, final boolean insert) {
        return insert ? "(%s)".formatted(str) : str;
    }

    protected String miniStringBuilder(final boolean left, final boolean right) {
        // :NOTE: simplified
        return insertBraces(f.toMiniString(), left && f instanceof Operation) + " " +
                symbol + " " + insertBraces(s.toMiniString(), right && f instanceof Operation);
    }

    // :NOTE: ??
    @Override
    public abstract String toMiniString();
}

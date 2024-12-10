package expression;

import java.util.Map;

public abstract class Operation extends BaseExpression {
    protected final BaseExpression f, s;
    private final char symbol;
    protected final int priority;

    public Operation(final BaseExpression f, final BaseExpression s, final char symbol, final int priority) {
        this.f = f;
        this.s = s;
        this.symbol = symbol;
        this.priority = priority;
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
        return insertBraces(f.toMiniString(), left) + " " +
                symbol + " " + insertBraces(s.toMiniString(), right);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Operation operation)) {
            return false;
        }
        return symbol == operation.symbol && f.equals(operation.f) && s.equals((operation.s));
    }
}

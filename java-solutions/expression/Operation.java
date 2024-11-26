package expression;

import java.util.Map;

public abstract class Operation extends BaseExpression {
    public BaseExpression f, s;
    public char symbol;

    public Operation(final BaseExpression f, final BaseExpression s, final char symbol) {
        this.f = f;
        this.s = s;
        this.symbol = symbol;
    }

    public abstract int operation(int a, int b);

    public abstract float operation(float a, float b);

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

    private String insertBraces(String str, boolean insert) {
        return insert ? "(%s)".formatted(str) : str;
    }

    protected String miniStringBuilder(boolean left, boolean right) {
        if (!(f instanceof Operation)) {
            left = false;
        }
        if (!(s instanceof Operation)) {
            right = false;
        }
        return insertBraces(f.toMiniString(), left) + " " + symbol + " " + insertBraces(s.toMiniString(), right);
    }

    @Override
    public abstract String toMiniString();

}

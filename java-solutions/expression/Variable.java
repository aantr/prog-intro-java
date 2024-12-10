package expression;

import java.util.Map;

public class Variable extends BaseExpression {
    public final String symbol;

    public Variable(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int evaluate(final int x) {
        return x;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        if (symbol.equals("x")) {
            return x;
        } else if (symbol.equals("y")) {
            return y;
        } else {
            return z;
        }
    }

    @Override
    public float evaluateF(final Map<String, Float> variables) {
        return variables.get(symbol);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Variable)) {
            return false;
        }
        return symbol.equals(((Variable) obj).symbol);
    }
}

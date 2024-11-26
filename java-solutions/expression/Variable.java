package expression;

import java.util.Map;

public class Variable extends BaseExpression {
    public String symbol;

    public Variable(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (symbol.equals("x")) {
            return x;
        } else if (symbol.equals("y")) {
            return y;
        }
        return z;
    }

    @Override
    public float evaluateF(Map<String, Float> variables) {
        return variables.get(symbol);
    }
}

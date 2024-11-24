package expression;

import java.util.HashSet;

public class Variable extends StringHashCode implements Expression {
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
}

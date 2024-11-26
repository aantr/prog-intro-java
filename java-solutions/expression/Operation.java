package expression;

import java.util.Map;

public abstract class Operation extends BaseExpression {
    public BaseExpression f, s;
    public char symbol;

    public Operation(BaseExpression f, BaseExpression s) {
        this.f = f;
        this.s = s;
    }

    public abstract int operation(int a, int b);

    public abstract double operation(double a, double b);

    @Override
    public int evaluate(int x) {
        return operation(f.evaluate(x), s.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operation(f.evaluate(x, y, z), s.evaluate(x, y, z));
    }

    @Override
    public double evaluateD(Map<String, Double> variables) {
        return operation(f.evaluateD(variables), s.evaluateD(variables));
    }

    @Override
    public String toString() {
        return "(%s %c %s)".formatted(f.toString(), symbol, s.toString());
    }

    @Override
    public String toMiniString() {
        boolean left = false, right = false;
        if (symbol == '-') {
            if (s.getClass() != Multiply.class && s.getClass() != Divide.class) {
                right = true;
            }
        }
        if (symbol == '*') {
            if (s.getClass() != Multiply.class) {
                right = true;
            }
            if (f.getClass() != Multiply.class && f.getClass() != Divide.class) {
                left = true;
            }
        }
        if (symbol == '/') {
            if (f.getClass() != Divide.class && f.getClass() != Multiply.class) {
                left = true;
            }
            right = true;

        }
        if (!(f instanceof Operation)) {
            left = false;
        }
        if (!(s instanceof Operation)) {
            right = false;
        }
        return (left ? "(" + f.toMiniString() + ")" : f.toMiniString()) +
                " " + symbol + " " +
                (right ? "(" + s.toMiniString() + ")" : s.toMiniString());
    }

}

package expression;

import java.util.Map;

public abstract class Operation extends BaseExpression {
    public BaseExpression f, s;
    // :NOTE: remove
    public char symbol;

    public Operation(final BaseExpression f, final BaseExpression s) {
        this.f = f;
        this.s = s;
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

    @Override
    public String toMiniString() {
        boolean left = false, right = false;
        // :NOTE: children
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
        // :NOTE: copy-paste
        return (left ? "(" + f.toMiniString() + ")" : f.toMiniString()) +
                " " + symbol + " " +
                (right ? "(" + s.toMiniString() + ")" : s.toMiniString());
    }

}

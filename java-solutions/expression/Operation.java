package expression;

public abstract class Operation extends StringHashCode implements Expression {
    public Expression f, s;
    public char symbol;

    public Operation(Expression f, Expression s) {
        this.f = f;
        this.s = s;
    }

    public abstract int getResult(int x);

    public abstract int getResult(int x, int y, int z);

    public int evaluate(int x) {
        return getResult(x);
    }

    public int evaluate(int x, int y, int z) {
        return getResult(x, y, z);
    }

    public String toString() {
        return "(%s %c %s)".formatted(f.toString(), symbol, s.toString());
    }

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

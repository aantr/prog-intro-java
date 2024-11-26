package expression;

import java.util.Map;

public class Const extends BaseExpression {
    private double value;
    private final boolean isDouble;

    public Const(int value) {
        this.value = value;
        isDouble = false;
    }

    public Const(double value) {
        this.value = value;
        isDouble = true;
    }

    @Override
    public int evaluate(int x) {
        return (int) value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) value;
    }

    @Override
    public double evaluateD(Map<String, Double> variables) {
        return value;
    }

    @Override
    public String toString() {
        if (isDouble) {
            return Double.toString(value);
        }
        return Integer.toString((int) value);
    }
}

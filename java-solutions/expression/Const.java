package expression;

import java.util.Map;

public class Const extends BaseExpression {
    public double value;

    public Const(int value) {
        this.value = value;
    }

    public Const(double value) {
        this.value = value;
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
        return Double.toString(value);
    }
}

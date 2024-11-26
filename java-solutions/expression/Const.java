package expression;

import java.util.Map;

public class Const extends BaseExpression {
    private float valueF = 0;
    private int value = 0;
    private final boolean isFloat;

    public Const(int value) {
        this.value = value;
        isFloat = false;
    }

    public Const(float value) {
        this.valueF = value;
        isFloat = true;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public float evaluateF(Map<String, Float> variables) {
        return valueF;
    }

    @Override
    public String toString() {
        if (isFloat) {
            return Float.toString(valueF);
        }
        return Integer.toString(value);
    }
}

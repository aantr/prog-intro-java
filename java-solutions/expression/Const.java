package expression;

import java.util.Map;

public class Const extends BaseExpression {
    private Object value = 0; // float int
    private final boolean isFloat;

    public Const(int value) {
        this.value = value;
        isFloat = false;
    }

    public Const(float value) {
        this.value = value;
        isFloat = true;
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
    public float evaluateF(Map<String, Float> variables) {
        return (float) value;
    }

    @Override
    public String toString() {
        if (isFloat) {
            return Float.toString((float) value);
        }
        return Integer.toString((int) value);
    }
}

package expression;

import java.util.Map;

public class Const extends BaseExpression {
    // :NOTE: Object
    // :NOTE: single field
    private final Object value; // float int
    private final boolean isFloat;

    public Const(final int value) {
        this.value = value;
        isFloat = false;
    }

    public Const(final float value) {
        this.value = value;
        isFloat = true;
    }

    @Override
    public int evaluate(final int x) {
        return (int) value;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
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

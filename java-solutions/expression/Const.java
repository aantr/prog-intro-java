package expression;

import java.util.Map;

public class Const extends BaseExpression {
    // :NOTE: Object
    private final Number value; // float int

    public Const(final int value) {
        this.value = value;
    }

    public Const(final float value) {
        this.value = value;
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

    private boolean isFloat() {
        return value.getClass() == Float.class;
    }

    @Override
    public String toString() {
        if (isFloat()) {
            return Float.toString((float) value);
        }
        return Integer.toString((int) value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Const)) {
            return false;
        }
        if (isFloat()) return (float) value == (float) ((Const) obj).value;
        return (int) value == (int) ((Const) obj).value;
    }
}

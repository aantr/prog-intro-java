package expression;

public class Divide extends Operation {
    public Divide(final BaseExpression f, final BaseExpression s) {
        super(f, s, '/');
    }

    @Override
    public int operation(final int a, final int b) {
        return a / b;
    }

    @Override
    public float operation(final float a, final float b) {
        return a / b;
    }

    @Override
    public String toMiniString() {
        boolean left = f.getClass() != Divide.class && f.getClass() != Multiply.class;
        return miniStringBuilder(left, true);
    }
}

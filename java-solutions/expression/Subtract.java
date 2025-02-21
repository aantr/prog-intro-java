package expression;

public class Subtract extends Operation {

    public Subtract(final BaseExpression f, final BaseExpression s) {
        super(f, s, '-');
    }

    @Override
    public int operation(final int a, final int b) {
        return a - b;
    }

    @Override
    public float operation(final float a, final float b) {
        return a - b;
    }

    @Override
    public String toMiniString() {
        boolean right = s.getClass() != Multiply.class && s.getClass() != Divide.class;
        return miniStringBuilder(false, right);
    }
}

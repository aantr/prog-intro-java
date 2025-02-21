package expression;

public class Multiply extends Operation {

    public Multiply(final BaseExpression f, final BaseExpression s) {
        super(f, s, '*');
    }

    @Override
    public int operation(final int a, final int b) {
        return a * b;
    }

    @Override
    public float operation(final float a, final float b) {
        return a * b;
    }

    @Override
    public String toMiniString() {
        boolean left = f.getClass() != Multiply.class && f.getClass() != Divide.class, right = s.getClass() != Multiply.class;
        return miniStringBuilder(left, right);
    }
}

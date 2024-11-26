package expression;

public class Add extends Operation {

    public Add(final BaseExpression f, final BaseExpression s) {
        super(f, s);
        symbol = '+';
    }

    @Override
    public int operation(final int a, final int b) {
        return a + b;
    }

    @Override
    public float operation(final float a, final float b) {
        return a + b;
    }
}

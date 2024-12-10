package expression;

public class Multiply extends Operation {

    public Multiply(final BaseExpression f, final BaseExpression s) {
        super(f, s, '*', 3);
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
        return miniStringBuilder(f instanceof Operation && ((Operation) f).priority < 2,
                s instanceof Operation && ((Operation) s).priority < 3);
    }
}

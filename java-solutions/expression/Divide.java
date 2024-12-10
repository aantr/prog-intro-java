package expression;

public class Divide extends Operation {
    public Divide(final BaseExpression f, final BaseExpression s) {
        super(f, s, '/', 2);
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
        return miniStringBuilder(f instanceof Operation && ((Operation) f).priority < 2, s instanceof Operation);
    }
}

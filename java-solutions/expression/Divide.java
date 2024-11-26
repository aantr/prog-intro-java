package expression;

public class Divide extends Operation {
    public Divide(BaseExpression f, BaseExpression s) {
        super(f, s, '/');
    }

    @Override
    public int operation(int a, int b) {
        return a / b;
    }

    @Override
    public float operation(float a, float b) {
        return a / b;
    }

    @Override
    public String toMiniString() {
        boolean left = f.getClass() != Divide.class && f.getClass() != Multiply.class;
        return miniStringBuilder(left, true);
    }
}

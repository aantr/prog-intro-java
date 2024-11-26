package expression;

public class Multiply extends Operation {

    public Multiply(BaseExpression f, BaseExpression s) {
        super(f, s, '*');
    }

    @Override
    public int operation(int a, int b) {
        return a * b;
    }

    @Override
    public float operation(float a, float b) {
        return a * b;
    }

    @Override
    public String toMiniString() {
        boolean left = f.getClass() != Multiply.class && f.getClass() != Divide.class, right = s.getClass() != Multiply.class;
        return miniStringBuilder(left, right);
    }
}

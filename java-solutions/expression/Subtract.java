package expression;

public class Subtract extends Operation {

    public Subtract(BaseExpression f, BaseExpression s) {
        super(f, s, '-');
    }

    @Override
    public int operation(int a, int b) {
        return a - b;
    }

    @Override
    public float operation(float a, float b) {
        return a - b;
    }

    @Override
    public String toMiniString() {
        boolean right = s.getClass() != Multiply.class && s.getClass() != Divide.class;
        return miniStringBuilder(false, right);
    }
}

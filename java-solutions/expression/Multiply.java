package expression;

public class Multiply extends Operation {

    public Multiply(BaseExpression f, BaseExpression s) {
        super(f, s);
        symbol = '*';
    }

    @Override
    public int operation(int a, int b) {
        return a * b;
    }

    @Override
    public float operation(float a, float b) {
        return a * b;
    }
}

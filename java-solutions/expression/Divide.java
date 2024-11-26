package expression;

public class Divide extends Operation {
    public Divide(BaseExpression f, BaseExpression s) {
        super(f, s);
        symbol = '/';
    }

    @Override
    public int operation(int a, int b) {
        return a / b;
    }
}

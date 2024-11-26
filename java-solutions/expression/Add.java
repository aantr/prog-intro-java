package expression;

public class Add extends Operation {

    public Add(BaseExpression f, BaseExpression s) {
        super(f, s);
        symbol = '+';
    }

    @Override
    public int operation(int a, int b) {
        return a + b;
    }
}

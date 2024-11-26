package expression;

public class Subtract extends Operation {

    public Subtract(BaseExpression f, BaseExpression s) {
        super(f, s);
        symbol = '-';
    }

    @Override
    public int operation(int a, int b) {
        return a - b;
    }

    @Override
    public double operation(double a, double b) {
        return a - b;
    }
}

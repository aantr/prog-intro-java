package expression;

public class Divide extends Operation {
    public Divide(Expression f, Expression s) {
        super(f, s);
        symbol = '/';
    }

    @Override
    public int getResult(int x) {
        return f.evaluate(x) / s.evaluate(x);
    }
}

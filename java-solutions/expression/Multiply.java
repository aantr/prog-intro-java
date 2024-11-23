package expression;

public class Multiply extends Operation {

    public Multiply(Expression f, Expression s) {
        super(f, s);
        symbol = '*';
    }

    @Override
    public int getResult(int x) {
        return f.evaluate(x) * s.evaluate(x);
    }
}

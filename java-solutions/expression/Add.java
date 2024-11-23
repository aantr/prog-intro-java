package expression;

public class Add extends Operation {

    public Add(Expression f, Expression s) {
        super(f, s);
        symbol = '+';
    }

    @Override
    public int getResult(int x) {
        return f.evaluate(x) + s.evaluate(x);
    }
}

package expression;

public class Subtract extends Operation{

    public Subtract(Expression f, Expression s) {
        super(f, s);
        symbol = '-';
    }

    @Override
    public int getResult(int x) {
        return f.evaluate(x) - s.evaluate(x);
    }

    @Override
    public int getResult(int x, int y, int z) {
        return 0;
    }
}

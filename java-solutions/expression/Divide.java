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

    @Override
    public int getResult(int x, int y, int z) {
        return 0;
//        return f.evaluate(x, y, z) / s.evaluate(x, y, z);
    }
}

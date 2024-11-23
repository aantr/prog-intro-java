package expression;

public class Variable implements Expression {
    public String symbol;

    public Variable(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

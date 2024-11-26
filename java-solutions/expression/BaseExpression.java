package expression;

public abstract class BaseExpression implements Expression, TripleExpression {
    public boolean equals(BaseExpression baseExpression) {
        return toString().equals(baseExpression.toString());
    }

    @Override
    public abstract String toString();

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}


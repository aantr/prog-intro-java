package expression;

public abstract class BaseExpression implements Expression, TripleExpression, FloatMapExpression {
    @Override
    public abstract boolean equals(final Object obj);

    public boolean equals(final BaseExpression baseExpression) {
        return equals((Object) baseExpression);
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


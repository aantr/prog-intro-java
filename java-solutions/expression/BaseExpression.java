package expression;

public abstract class BaseExpression implements Expression, TripleExpression, FloatMapExpression {
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseExpression)) {
            return false;
        }
        return toString().equals(obj.toString());
    }
    public boolean equals(BaseExpression baseExpression) {
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


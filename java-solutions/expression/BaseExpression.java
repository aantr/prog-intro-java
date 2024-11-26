package expression;

public abstract class BaseExpression implements Expression, TripleExpression {
    public boolean equals(Object obj) {
        if (obj instanceof BaseExpression) {
            return toString().equals(obj.toString());
        }
        return false;
    }

//    public boolean equals(ToMiniString baseExpression) {
//        return equals((Object) baseExpression);
//    }

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


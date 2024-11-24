package expression;

public class StringHashCode {
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

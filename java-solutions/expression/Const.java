package expression;

import java.util.HashSet;

public class Const extends StringHashCode implements Expression {
    public int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

//    @Override
//    public int evaluate(int x, int y, int z) {
//        return value;
//    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}

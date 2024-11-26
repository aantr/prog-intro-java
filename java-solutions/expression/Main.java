package expression;

import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int x = scanner.nextInt();
            Expression expr = new Add(new Subtract(new Multiply(new Variable("x"), new Variable("x")), new Multiply(
                    new Variable("x"), new Const(2))), new Const(1));
            System.out.println(expr.evaluate(x));
        }
    }
}

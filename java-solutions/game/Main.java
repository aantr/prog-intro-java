package game;

import java.util.Scanner;

import static java.lang.Math.max;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {

    private static int n, m, k;

    private static boolean readNMK() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter n, m, k: ");
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        return n >= 1 && m >= 1 && k <= max(n, m);
    }

    public static void main(String[] args) {
        final Game game = new Game(false, new HumanPlayer(), new HumanPlayer());
        int result;
        do {
            while (!readNMK()) {

            }
            result = game.play(new MNKBoard(n, m, k));
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}

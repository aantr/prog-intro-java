package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import static game.MNKBoard.isValidNMK;

public class Reader {
    public int players, n, m, k;
    private final PrintStream out;
    private final Scanner scanner;

    Reader(final PrintStream out, final Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
    }

    private boolean readPlayers() {
        out.print("Enter number of players: ");
        try {
            players = scanner.nextInt();
        } catch (final InputMismatchException ignored) {
            scanner.next();
            return false;
        }
        return players >= 1;
    }

    private boolean readNMK() {
        try {
            out.print("Enter n, m, k: ");
            n = scanner.nextInt();
            m = scanner.nextInt();
            k = scanner.nextInt();
            return isValidNMK(n, m, k);
        } catch (final InputMismatchException ignored) {
            scanner.next();
            return false;
        }
    }

    public void read() {
        while (!readPlayers()) {
            System.out.println("Number is invalid");
        }
        while (!readNMK()) {
            System.out.println("Numbers n, m, k are invalid");
        }
    }

}

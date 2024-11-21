package game;

import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final SwissSystem tournament = new SwissSystem(() -> new HumanPlayer(scanner), System.out, scanner);
            Contestant[] result = tournament.play();
            for (Contestant contestant : result) {
                System.out.println("Contestant #" + (contestant.id + 1) + ": " + contestant.points + " points");
            }
        }
    }
}

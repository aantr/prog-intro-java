package game;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final SwissSystem tournament = new SwissSystem(
//                    (int n, int m, int k) -> new RandomPlayer(n, m), System.out, scanner
                    (int n, int m, int k) -> new HumanPlayer(System.out, scanner), System.out, scanner
            );
            tournament.play();
            System.out.println(tournament);
        }
    }
}

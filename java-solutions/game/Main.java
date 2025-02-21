package game;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final Reader reader = new Reader(System.out, scanner);
            reader.read();
            final SwissSystem tournament = new SwissSystem(reader.players, reader.n, reader.m, reader.k,
//                    (int n, int m, int k) -> new RandomPlayer(n, m), System.out, scanner
                    (int n, int m, int k) -> new HumanPlayer(System.out, scanner), System.out, scanner
            );
            tournament.play();
            // :NOTE: tournament.playTour();
//            tournament.getStandings();
            System.out.println(tournament);
        }
    }
}

package game;

import java.util.HashSet;
import java.util.Scanner;

import static java.util.Arrays.sort;

public class SwissSystem {
    private int n;
    private final Contestant[] contestants;
    private final PlayerFabric playerFabric;
    private final HashSet[] games;

    private boolean readPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        n = scanner.nextInt();
        return n >= 1;
    }

    @FunctionalInterface
    public interface PlayerFabric {
        Player getPlayer();
    }

    public SwissSystem(PlayerFabric playerFabric) {
        this.playerFabric = playerFabric;
        while (!readPlayers()) {
            System.out.println("Number is incorrect, try again");
        }
        contestants = new Contestant[n];
        games = new HashSet[n];
        for (int i = 0; i < n; i++) {
            contestants[i] = new Contestant();
            games[i] = new HashSet<Integer>();
            contestants[i].id = i;
        }
    }

    public Contestant[] play() {
        int numTours = (int) Math.ceil(Math.log(n) / Math.log(2));
        for (int tour = 0; tour < numTours; tour++) {
            System.out.println("Tour #" + (tour + 1) + ": ");
            playTour();
            sort(contestants, (o1, o2) -> o2.points - o1.points);
        }
        return contestants;
    }

    public void playTour() {
        int groupStart = 0;
        while (groupStart < n) {
            int groupEnd = groupStart;
            while (groupEnd < n && contestants[groupEnd].points == contestants[groupStart].points) {
                groupEnd++;
            }
            boolean oneLeft = false;
            if ((groupEnd - groupStart) % 2 != 0) {
                if (groupEnd < n) {
                    groupEnd++;
                } else {
                    oneLeft = true;
                }
            }
            int segmentL = groupStart, segmentR = groupEnd;
            while (segmentR - segmentL >= 2) {
                int x = segmentL++;
                int y = --segmentR;

                playGame(x, y);
            }
            groupStart = groupEnd;
            if (oneLeft) {
                contestants[groupEnd - 1].points++;
            }
        }
    }

    private void playGame(int player0, int player1) {
        Game game = new Game(false, playerFabric.getPlayer(), playerFabric.getPlayer());
        int result = game.play(new MNKBoard());
        System.out.println(
                "Player #" + (contestants[player0].id + 1) + " is X, " +
                        "Player #" + (contestants[player1].id + 1) + " is O"
        );
        if (result == 1) {
            contestants[player0].points++;
        } else if (result == 2) {
            contestants[player1].points++;
        }
    }
}

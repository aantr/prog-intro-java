package game;

import java.io.PrintStream;
import java.util.*;

import static game.MNKBoard.isValidNMK;
import static java.util.Arrays.sort;

public class SwissSystem {
    private int number;
    private int n, m, k;
    private final Contestant[] contestants;
    private final PlayerFabric playerFabric;
    private final Set[] games;
    private final PrintStream out;
    private final Scanner scanner;
    private final Random random;

    private boolean readPlayers() {
        out.print("Enter number of players: ");
        try {
            number = scanner.nextInt();
        } catch (InputMismatchException ignored) {
            scanner.next();
            return false;
        }
        return number >= 1;
    }

    private boolean readNMK() {
        try {
            out.print("Enter n, m, k: ");
            n = scanner.nextInt();
            m = scanner.nextInt();
            k = scanner.nextInt();
            return isValidNMK(n, m, k);
        } catch (InputMismatchException ignored) {
            scanner.next();
            return false;
        }
    }

    @FunctionalInterface
    public interface PlayerFabric {
        Player getPlayer();
    }

    public SwissSystem(PlayerFabric playerFabric, final PrintStream out, final Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
        this.playerFabric = playerFabric;
        this.random = new Random();
        while (!readPlayers()) {
            System.out.println("Number is invalid");
        }
        while (!readNMK()) {
            out.println("Numbers n, m, k are invalid");
        }
        contestants = new Contestant[number];
        games = new Set[number];
        for (int i = 0; i < number; i++) {
            contestants[i] = new Contestant();
            games[i] = new HashSet<Integer>();
            contestants[i].id = i;
        }
    }

    public Contestant[] play() {
        int numTours = (int) Math.ceil(Math.log(number) / Math.log(2));
        for (int tour = 0; tour < numTours; tour++) {
            System.out.println("Tour #" + (tour + 1) + ": ");
            playTour();
            sort(contestants, (o1, o2) -> o2.points - o1.points);
        }
        return contestants;
    }

    public void playTour() {
        int groupStart = 0;
        while (groupStart < number) {
            int groupEnd = groupStart;
            while (groupEnd < number && contestants[groupEnd].points == contestants[groupStart].points) {
                groupEnd++;
            }
            boolean oneLeft = false;
            if ((groupEnd - groupStart) % 2 != 0) {
                if (groupEnd < number) {
                    groupEnd++;
                } else {
                    oneLeft = true;
                }
            }
            int segmentL = groupStart, segmentR = groupEnd;
            while (segmentR - segmentL >= 2) {
                int x = segmentL++;
                int y = --segmentR;

                if (random.nextBoolean()) {
                    playGame(y, x);
                } else {
                    playGame(x, y);
                }
            }
            groupStart = groupEnd;
            if (oneLeft) {
                contestants[groupEnd - 1].points += 2;
            }
        }
    }

    private void playGame(int player0, int player1) {
        out.println(
                "Player #" + (contestants[player0].id + 1) + " is %c, ".formatted(MNKBoard.SYMBOLS.get(Cell.X)) +
                        "Player #" + (contestants[player1].id + 1) + " is %c".formatted(MNKBoard.SYMBOLS.get(Cell.O))
        );
        Game game = new Game(true, playerFabric.getPlayer(), playerFabric.getPlayer());

        int result = game.play(new MNKBoard(n, m, k));

        if (result == 1) {
            contestants[player0].points += 2;
        } else if (result == 2) {
            contestants[player1].points += 2;
        } else {
            contestants[player0].points++;
            contestants[player1].points++;
        }
    }
}

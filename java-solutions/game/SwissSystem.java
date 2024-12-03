package game;

import java.io.PrintStream;
import java.util.*;

import static game.MNKBoard.isValidNMK;
import static java.util.Arrays.sort;

public class SwissSystem {
    private int number;
    private int n, m, k;
    private int numTours;

    private final PrintStream out;
    private final Scanner scanner;
    private final Random random;

    private final Contestant[] contestants;
    private final PlayerFabric playerFabric;
    private final ArrayList<Set<Integer>> games;
    private final Set<Integer> oneLeft;

    private boolean readPlayers() {
        out.print("Enter number of players: ");
        try {
            number = scanner.nextInt();
        } catch (final InputMismatchException ignored) {
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
        } catch (final InputMismatchException ignored) {
            scanner.next();
            return false;
        }
    }

    @FunctionalInterface
    public interface PlayerFabric {
        Player getPlayer(int n, int m, int k);
    }

    public SwissSystem(final PlayerFabric playerFabric, final PrintStream out, final Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
        this.playerFabric = playerFabric;
        this.random = new Random(1234);
        this.oneLeft = new HashSet<>();
        while (!readPlayers()) {
            System.out.println("Number is invalid");
        }
        while (!readNMK()) {
            out.println("Numbers n, m, k are invalid");
        }
        contestants = new Contestant[number];
        games = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            contestants[i] = new Contestant();
            contestants[i].id = i;
            games.add(new HashSet<>());
        }
    }

    // :NOTE: no results
    public void play() {
        numTours = (int) Math.ceil(Math.log(number) / Math.log(2));
        for (int tour = 0; tour < numTours; tour++) {
            out.println("Tour #" + (tour + 1) + ": ");
            out.print(this);
            playTour();
            sort(contestants, (o1, o2) -> o2.points - o1.points);
        }
    }

    private void shuffle(final Object[] arr, final int l, final int r) {
        for (int i = r - l - 1; i > 0; i--) {
            final int index = random.nextInt(i + 1);
            // swap
            final Object a = arr[index + l];
            arr[index + l] = arr[i + l];
            arr[i + l] = a;
        }
        swapLast();
    }

    private void swapLast() {
        if (oneLeft.contains(contestants[number - 1].id)) {
            for (int i = number - 2; i >= 0; i--) {
                if (!oneLeft.contains(contestants[i].id)) {
                    final Contestant temp = contestants[number - 1];
                    contestants[number - 1] = contestants[i];
                    contestants[i] = temp;
                }
            }
        }
    }

    // trying to pair by rules then shuffle if failed
    private void playTourGames(final int segmentL, final int segmentR) {
        final HashSet<Integer> played = new HashSet<>();
        while (true) {
            boolean error = false;
            final ArrayList<ArrayList<Integer>> addedGames = new ArrayList<>();
            for (int i = segmentL; i < (segmentL + segmentR) / 2; i++) { // first half
                if (played.contains(contestants[i].id)) {
                    continue;
                }
                for (int j = (segmentR + segmentL) / 2; j < segmentR; j++) { // pair with second half
                    if (makeGame(addedGames, played, i, j)) {
                        break;
                    }
                }
                if (!played.contains(contestants[i].id)) {
                    error = true;
                    for (final var el : addedGames) {
                        games.get(el.get(0)).remove(el.get(1));
                        games.get(el.get(1)).remove(el.get(0));
                    }
                    break;
                }
            }
            if (!error) {
                break;
            }
            shuffle(contestants, segmentL, segmentR);
        }
    }

    public void playTour() {
        int groupStart = 0;
        while (groupStart < number) {
            int groupEnd = groupStart;
            while (groupEnd < number && (groupEnd - groupStart < numTours ||
                    contestants[groupEnd].points == contestants[groupStart].points)) {
                groupEnd++;
            }
            if (number - groupEnd < numTours) {
                groupEnd = number;
            }
            final int segmentL = groupStart;
            int segmentR = groupEnd;
            if ((segmentR - segmentL) % 2 == 1) {
                segmentR++;
                if (segmentR >= number) {
                    segmentR--;
                }
            }

            // keep last has no free wins
            swapLast();

            // play games
            playTourGames(segmentL, segmentR);

            // mov
            groupStart = groupEnd;
            if ((segmentR - segmentL) % 2 == 1) {
                contestants[segmentR - 1].points += 2;
                oneLeft.add(contestants[segmentR - 1].id);
            }
        }
    }

    private boolean makeGame(final ArrayList<ArrayList<Integer>> addedGames, final HashSet<Integer> played, final int i, final int j) {
        if (!played.contains(contestants[j].id) && !games.get(contestants[i].id).contains(contestants[j].id)) {
            games.get(contestants[i].id).add(contestants[j].id);
            games.get(contestants[j].id).add(contestants[i].id);
            played.add(contestants[i].id);
            played.add(contestants[j].id);
            addedGames.add(new ArrayList<>(List.of(contestants[i].id, contestants[j].id)));
            if (random.nextBoolean()) {
                playGame(j, i);
            } else {
                playGame(i, j);
            }
            return true;
        }
        return false;
    }

    private void playGame(final int player0_idx, final int player1_idx) {
        out.println("Player #" + (contestants[player0_idx].id + 1) +
                " is %c, ".formatted(MNKBoard.SYMBOLS.get(Cell.X)) +
                "Player #" + (contestants[player1_idx].id + 1) +
                " is %c".formatted(MNKBoard.SYMBOLS.get(Cell.O))
        );
        final Game game = new Game(true, playerFabric.getPlayer(n, m, k), playerFabric.getPlayer(n, m, k));

        final int result = game.play(new MNKBoard(n, m, k, true));

        if (result == 1) {
            contestants[player0_idx].points += 2;
        } else if (result == 2) {
            contestants[player1_idx].points += 2;
        } else {
            contestants[player0_idx].points++;
            contestants[player1_idx].points++;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tournament standings:\n");
        for (final Contestant contestant : contestants) {
            sb.append("Contestant #").append(contestant.id + 1).append(": ").
                    append(contestant.points).append(" points\n");
        }
        return sb.toString();
    }
}

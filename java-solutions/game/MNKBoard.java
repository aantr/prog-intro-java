package game;

import java.util.Map;
import java.util.Scanner;

import static java.lang.Math.max;

public class MNKBoard implements Board {
    public static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final MNKPosition position;
    private int empty;
    private int n, m, k;

    private boolean isValidNMK() {
        return n >= 1 && m >= 1 && k <= max(n, m);
    }

    private boolean readNMK() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter n, m, k: ");
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        return isValidNMK();
    }

    public MNKBoard() {
        while (!readNMK()) {
            System.out.println("n, m, k are incorrect, try again");
        }
        this.empty = n * m;
        position = new MNKPosition(n, m);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Cell getCell() {
        return position.turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!position.isValid(move)) {
            return Result.LOSE;
        }

        position.cells[move.row()][move.column()] = move.value();
        empty--;

        int[][] vectors = {{1, 1}, {0, 1}, {1, 0}};
        for (var vector : vectors) {
            int length = 0;
            for (int i = 0; i < 2; i++) {
                int[] cur = {move.row(), move.column()};
                length += getMaxSubsequence(vector, cur);
            }
            if (length + 1 >= k) {
                return Result.WIN;
            }
        }


        if (empty == 0) {
            return Result.DRAW;
        }

        position.turn = position.turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    private int getMaxSubsequence(int[] vector, int[] cur) {
        int length = 0;
        while (position.isCurrent(cur[0] + vector[0], cur[1] + vector[1])) {
            cur[0] += vector[0];
            cur[1] += vector[1];
            length++;
        }
        vector[0] *= -1;
        vector[1] *= -1;
        return length;
    }

}

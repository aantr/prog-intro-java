package game;

import java.util.Map;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class MNKBoard implements Board {
    public static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private static final int[][] VECTORS = new int[][]{{1, 1}, {0, 1}, {1, 0}};

    private int empty;
    private final int k;

    private final Cell[][] cells;
    private Cell turn;
    private final boolean rhombus;

    static boolean isValidNMK(final int n, final int m, final int k) {
        return n >= 1 && m >= 1 && k <= max(n, m);
    }

    public MNKBoard(int n, int m, final int k, final boolean rhombus) {
        assert !rhombus || n == m;
        assert isValidNMK(n, m, k);
        this.rhombus = rhombus;
        this.k = k;
        this.empty = n * m;
        if (rhombus) {
            n = n * 2 - 1;
            m = n;
        }
        cells = new Cell[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                cells[i][j] = Cell.E;
        turn = Cell.X;
    }

    @Override
    public MNKPosition getPosition() {
        return new MNKPosition(cells, turn, rhombus);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!getPosition().isValid(move)) {
            return Result.LOSE;
        }

        cells[move.row()][move.column()] = move.value();
        empty--;

        for (final var vector : VECTORS) {
            // :NOTE: simplified
            if (getMaxSubsequence(move, vector[0], vector[1]) +
                    getMaxSubsequence(move, -vector[0], -vector[1]) + 1 >= k) {
                return Result.WIN;
            }
        }
        if (empty == 0) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }


    private int getMaxSubsequence(final Move move, final int dr, final int dc) {
        int r = move.row();
        int c = move.column();
        int length = 0;
        while (getPosition().isCurrent(r + dr, c + dc)) {
            r += dr;
            c += dc;
            length++;
        }
        return length;
    }

}
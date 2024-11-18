package game;

import java.util.Arrays;
import java.util.Map;

import static java.lang.Math.max;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private final int k;
    private Cell turn;
    private int empty;

    public MNKBoard(int n, int m, int k) {
        assert n >= 1 && m >= 1 && k <= max(n, m);
        this.cells = new Cell[n][m];
        this.k = k;
        this.empty = n * m;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        cells[move.row()][move.column()] = move.value();
        empty--;

        int[][] vectors = {{1, 1}, {0, 1}, {1, 0}};
        for (var vector : vectors) {
            int length = 0;
            for (int i = 0; i < 2; i++) {
                int[] cur = {move.row(), move.column()};
                length += getMaxSubsequence(vector, cur);
            }
            System.out.println(length);
            if (length + 1 >= k) {
                return Result.WIN;
            }
        }


        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    private int getMaxSubsequence(int[] vector, int[] cur) {
        int length = 0;
        while (isCurrent(cur[0] + vector[0], cur[1] + vector[1])) {
            cur[0] += vector[0];
            cur[1] += vector[1];
            length++;
        }
        vector[0] *= -1;
        vector[1] *= -1;
        return length;
    }

    private boolean isValidPosition(int r, int c) {
        return 0 <= r && r < cells.length
                && 0 <= c && c < cells[0].length;
    }

    private boolean isCurrent(int r, int c) {
        return isValidPosition(r, c) &&
                cells[r][c] == turn;
    }

    @Override
    public boolean isValid(final Move move) {
        return isValidPosition(move.row(), move.column())
                && cells[move.row()][move.column()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < cells[0].length; i++) {
            sb.append(i);
        }
        for (int r = 0; r < cells.length; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < cells[0].length; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}

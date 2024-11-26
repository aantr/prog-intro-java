package game;

import java.util.Arrays;

import static game.MNKBoard.SYMBOLS;
import static java.lang.Math.*;

public class MNKPosition implements Position {
    private final Cell[][] cells;
    private Cell turn;
    private final boolean rotated;

    public MNKPosition(int n, int m, boolean rotated) {
        this.rotated = rotated;
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public boolean isValid(final Move move) {
        return isValidPosition(move.row(), move.column())
                && cells[move.row()][move.column()] == Cell.E;
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public int getN() {
        return cells.length;
    }

    @Override
    public void setCell(final int r, final int c, final Cell v) {
        cells[r][c] = v;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public void setTurn(Cell v) {
        turn = v;
    }

    @Override
    public boolean isRotated() {
        return rotated;
    }

    private boolean isValidPosition(int r, int c) {
        return 0 <= r && r < cells.length
                && 0 <= c && c < cells[0].length;
    }

    public boolean isCurrent(int r, int c) {
        return isValidPosition(r, c) &&
                cells[r][c] == turn;
    }

    @Override
    public String toString() {
        final StringBuilder sb;
        if (!rotated) {
            sb = new StringBuilder().repeat(' ', 3);
            for (int i = 0; i < cells[0].length; i++) {
                sb.append(i + 1).repeat(' ', 3 - String.valueOf(i + 1).length());
            }
            for (int r = 0; r < cells.length; r++) {
                sb.append('\n');
                sb.append(r + 1).repeat(' ', 3 - String.valueOf(r + 1).length());

                for (int c = 0; c < cells[0].length; c++) {
                    sb.append(SYMBOLS.get(cells[r][c])).repeat(' ', 2);
                }
            }
        } else {
            sb = new StringBuilder().repeat(' ', 5);
            for (int i = 0; i < cells.length + cells[0].length - 1; i++) {
                sb.append(i + 1).repeat(' ', 3 - String.valueOf(i + 1).length());
            }
            for (int d = 0; d < cells[0].length + cells.length - 1; d++) {
                sb.append('\n');
                sb.append(d + 1).repeat(' ', 5 - String.valueOf(d + 1).length())
                        .repeat(' ', abs(d - cells.length + 1) * 3);
                for (int r = min(cells.length - 1, d); r >= max(0, d - cells[0].length + 1); r--) {
                    int c = d - r;
                    sb.append(SYMBOLS.get(cells[r][c]));
                    if (r >  max(0, d - cells[0].length + 1)) {
                        sb.repeat(' ', 5);
                    }
                }
            }
        }
        return sb.toString();

    }
}

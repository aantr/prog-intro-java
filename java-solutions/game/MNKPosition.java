package game;

import java.util.Arrays;

import static game.MNKBoard.SYMBOLS;

public class MNKPosition implements Position {

    public final Cell[][] cells;
    public Cell turn;

    public MNKPosition(int n, int m) {
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

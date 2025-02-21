package game;

import static game.MNKBoard.SYMBOLS;
import static java.lang.Math.abs;

public class MNKPosition implements Position {
    private final Cell[][] cells;
    private final Cell turn;
    private final boolean rhombus;

    public MNKPosition(final Cell[][] cells, final Cell turn, final boolean rhombus) {
        this.rhombus = rhombus;
        this.cells = cells;
        this.turn = turn;
    }

    @Override
    public boolean isValid(final Move move) {
        return isValidPosition(move.row(), move.column())
                && cells[move.row()][move.column()] == Cell.E;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String getString() {
        final StringBuilder sb;
        if (!rhombus) {
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
            sb = new StringBuilder().repeat(' ', 3);
            for (int i = 0; i < cells[0].length; i++) {
                sb.append(i + 1).repeat(' ', 3 - String.valueOf(i + 1).length());
            }
            for (int r = 0; r < cells.length; r++) {
                sb.append('\n');
                sb.append(r + 1).repeat(' ', 3 - String.valueOf(r + 1).length());
                sb.repeat(' ', 3 * abs(r - cells.length / 2));
                for (int c = abs(r - cells.length / 2); c < cells[0].length - abs(r - cells.length / 2); c++) {
                    sb.append(SYMBOLS.get(cells[r][c])).repeat(' ', 2);
                }
            }
        }
        return sb.toString();
    }

    public boolean getRhombus() {
        return rhombus;
    }

    private boolean isValidPosition(final int r, final int c) {
        if (rhombus) {
            return cells.length / 2 <= r + c &&
                    cells.length / 2 <= r + cells.length - 1 - c &&
                    cells.length / 2 <= cells.length - 1 - r + c &&
                    cells.length / 2 <= cells.length - 1 - r + cells.length - 1 - c;
        }
        return 0 <= r && r < cells.length
                && 0 <= c && c < cells[0].length;
    }

    public boolean isCurrent(final int r, final int c) {
        return isValidPosition(r, c) &&
                cells[r][c] == turn;
    }
}
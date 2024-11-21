package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Position {
    boolean isValid(Move move);

    Cell getCell(int r, int c);

    void setCell(int r, int c, Cell v);

    Cell getTurn();

    void setTurn(Cell v);
}

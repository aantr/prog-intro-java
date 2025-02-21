package game;

public interface Position {
    boolean isValid(Move move);
    // :NOTE: not enough information
    Cell getTurn();
    Cell getCell(int r, int c);

    String getString();
}

package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public record Move(int row, int column, Cell value, boolean draw, boolean resign) {
    @Override
    public String toString() {
        if (draw) {
            return "draw=true";
        }
        if (resign) {
            return "resign=true";
        }
        return "row=" + row + ", column=" + column + ", value=" + value;
    }
}

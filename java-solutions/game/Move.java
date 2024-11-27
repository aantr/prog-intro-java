package game;


public record Move(int row, int column, Cell value, boolean draw, boolean resign) {
    @Override
    public String toString() {
        if (draw) {
            return "draw=true";
        }
        if (resign) {
            return "resign=true";
        }
        return "row=" + (row + 1) + ", column=" + (column + 1) + ", value=" + value;
    }
}

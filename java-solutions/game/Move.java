package game;


public record Move(int hv, int row, int column, Cell value) {
    @Override
    public String toString() {
        return "h/v = " + hv + "row=" + (row + 1) + ", column=" + (column + 1) + ", value=" + value;
    }
}

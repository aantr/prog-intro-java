package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position.getString());
            out.println(cell + "'s move");
            try {
                int hv = in.nextInt();
                int first = in.nextInt();
                int second = in.nextInt();
                final Move move = new Move(hv, first, second, cell);
                if (position.isValid(move)) {
                    return move;
                }
                out.println("Move " + move + " is invalid");
            } catch (InputMismatchException ignored) {
                in.next();
                out.println("A number is invalid");
            }
        }
    }

}

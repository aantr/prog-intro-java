package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer(final Scanner in) {
        this(System.out, in);
    }

    @Override
    public Move move(final Position position, final Cell cell, final boolean prevOffer) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            if (!prevOffer) {
                out.println("Enter row and column / -1 to offer a draw / -2 to resign");
            } else {
                out.println("Enter row and column / -2 to resign");
            }
            try {
                int first = in.nextInt();
                if (first == -1 && !prevOffer) {
                    return new Move(0, 0, cell, true, false);
                }
                if (first == -2) {
                    return new Move(0, 0, cell, false, true);
                }
                final Move move = new Move(first, in.nextInt(), cell, false, false);
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

    @Override
    public int drawResponse() {
        out.println("Opponent offers a draw [yes/No]: ");
        String ans = in.next();
        return ans.equalsIgnoreCase("yes") ? 1 : 0;
    }
}

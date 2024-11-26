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
                first--;
                int second = in.nextInt();
                second--;
                boolean valid = true;
                if (position.isRotated()) {
                    second = (second - first - position.getN() + 1);
                    if (second % 2 != 0) {
                        valid = false;
                    }
                    int row = second / -2;
                    second = first - row;
                    first = row;
                }
                final Move move = new Move(first, second, cell, false, false);
                if (position.isValid(move) && valid) {
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

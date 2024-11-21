package game;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell, final boolean prevOffer) {
        while (true) {
            int r = random.nextInt(3);
            int c = random.nextInt(3);
            final Move move = new Move(r, c, cell, false, false);
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public int drawResponse() {
        return 0;
    }
}

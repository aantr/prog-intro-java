package game;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;
    private final int n, m;

    public RandomPlayer(final Random random, final int n, final int m) {
        this.random = random;
        this.n = n;
        this.m = m;
    }

    public RandomPlayer(final int n, final int m) {
        this(new Random(), n, m);
    }

    @Override
    public Move move(final Position position, final Cell cell, final boolean prevOffer) {
        while (true) {
            int r = random.nextInt(n);
            int c = random.nextInt(m);
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

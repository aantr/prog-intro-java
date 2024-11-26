package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final boolean log;
    private final Player player1, player2;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        boolean wasOffer = false;
        boolean prevOffer = false;
        int who = 0;
        while (true) {
            Player player = who == 0 ? player1 : player2;
            who = 1 - who;
            final int result = move(board, player, who + 1, wasOffer, prevOffer);
            if (result == -2) {
                wasOffer = true;
                continue;
            }
            if (prevOffer) {
                prevOffer = false;
            }
            if (wasOffer) {
                wasOffer = false;
                prevOffer = true;
                if (result == 1) { // accepted draw
                    return 0;
                }
                continue;
            }
            if (result != -1) {
                return result;
            }
        }
    }

    private int move(final Board board, final Player player, final int no, final boolean wasOffer, final boolean prevOffer) {
        if (wasOffer) {
            boolean result = player.drawResponse();
            if (result) {
                log("Player " + no + " accepts a draw");
            } else {
                log("Player " + no + " declined a draw");
            }
            return result ? 1 : 0;
        }
        final Move move = player.move(board.getPosition(), board.getCell(), prevOffer);
        if (move.resign()) {
            log("Player " + no + " resign");
            log("Player " + (3 - no) + " won");
            return 3 - no;
        }
        if (move.draw() && !prevOffer) {
            return -2;
        }
        final Result result = board.makeMove(move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board.getPosition());
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}

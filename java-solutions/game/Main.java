package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final SwissSystem tournament = new SwissSystem(RandomPlayer::new);
        Contestant[] result = tournament.play();
        for (Contestant contestant : result) {
            System.out.println("Contestant #" + (contestant.id + 1) + ": " + contestant.points + " points");
        }
    }
}

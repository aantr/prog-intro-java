import java.io.IOException;
import java.io.InputStreamReader;

public class Reverse {
    private final static int MAX_N = 3_000_000;

    public static void main(String[] args) {
        try (MyScanner sc = new MyScanner(new InputStreamReader(System.in))) {
            int[] numbers = new int[MAX_N];
            int current = 0;
            while (sc.hasNextLine()) {
                numbers[current++] = Integer.MAX_VALUE;
                String read;
                while (!(read = sc.nextOrLineSeparator(MyScanner::isValidInt)).isEmpty()) {
                    numbers[current++] = Integer.parseInt(read);
                }
            }

            for (int i = 0; i < current; i++) {
                int n = numbers[current - 1 - i];
                if (n == Integer.MAX_VALUE) {
                    System.out.print("\n");
                } else {
                    System.out.print(n + " ");
                }
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        } catch (ScannerException e) {
            System.err.println("Scanner error: " + e.getMessage());
        }
    }
}

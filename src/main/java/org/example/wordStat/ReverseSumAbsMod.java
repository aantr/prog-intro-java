import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.lang.Math.abs;

public class ReverseSumAbsMod {

    static final int MOD = 1_000_000_007;

    private static int addMod(int x, int y) {
        return ((x % MOD + y % MOD) % MOD + MOD) % MOD;
    }

    private static int subMod(int x, int y) {
        return ((x % MOD - y % MOD) % MOD + MOD) % MOD;
    }

    public static void main(String[] args) {
        int[][] numbers = new int[1][];

        int maxCols = 0;
        int row = 0;
        try {
            MyScanner scanner = new MyScanner(new InputStreamReader(System.in));
            try {
                while (scanner.hasNextLine()) {
                    int col = 0;
                    int[] currentRow = new int[1];
                    String read;
                    while (!(read = scanner.nextOrSeparator(MyScanner::isValidInt)).isEmpty()) {
                        int nextInt = Integer.parseInt(read);
                        if (col >= currentRow.length) {
                            currentRow = Arrays.copyOf(currentRow, currentRow.length * 2);
                        }
                        currentRow[col++] = nextInt;
                        maxCols = Math.max(maxCols, col);
                    }
                    if (row >= numbers.length) {
                        numbers = Arrays.copyOf(numbers, numbers.length * 2);
                    }
                    numbers[row++] = Arrays.copyOf(currentRow, col);
                }
                // shrink to fit cols
                numbers = Arrays.copyOf(numbers, row);

                int[] rows = new int[row];
                int[] cols = new int[maxCols];
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < numbers[i].length; j++) {
                        rows[i] = addMod(rows[i], abs(numbers[i][j]));
                        cols[j] = addMod(cols[j], abs(numbers[i][j]));
                    }
                }
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < numbers[i].length; j++) {
                        System.out.print(subMod(addMod(rows[i], cols[j]), abs(numbers[i][j])) + " ");
                    }
                    System.out.println();
                }
            } catch (ScannerException e) {
                System.err.println("Scanner error: " + e.getMessage());
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }
}


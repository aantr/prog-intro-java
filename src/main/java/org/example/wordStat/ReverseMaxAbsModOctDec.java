import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ReverseMaxAbsModOctDec {

    static final int MOD = 1_000_000_007;

    static int max(int a, int b) {
        if (Math.abs(a) % MOD > Math.abs(b) % MOD) {
            return a;
        }
        return b;
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
                    while (!scanner.hasNextLineSeparatorIntOct()) {
                        int nextInt;
                        try {
                            nextInt = scanner.nextIntOct();
                        } catch (ScannerException e) {
                            System.err.println("Scanner error: " + e.getMessage());
                            return;
                        }
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
                        rows[i] = max(rows[i], numbers[i][j]);
                        cols[j] = max(cols[j], numbers[i][j]);
                    }
                }

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < numbers[i].length; j++) {
                        System.out.print(Integer.toOctalString(max(rows[i], cols[j])) + "o ");
                    }
                    System.out.println();
                }
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }
}


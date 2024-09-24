import static java.lang.Math.abs;

import java.util.Scanner;

public class ReverseSumAbsMod {
    final static int mod = 1_000_000_007;

    private static int add_mod(int x, int y) {
        return ((x % mod + y % mod) % mod + mod) % mod;
    }

    private static int sub_mod(int x, int y) {
        return ((x % mod - y % mod) % mod + mod) % mod;
    }

    public static void main(String[] args) {
        int[][] numbers = new int[1][1];

        Scanner sc = new Scanner(System.in);
        int max_rows = 0;
        int max_cols = 0;
        int row = 0;
        while (sc.hasNextLine()) {
            if (row + 1 > numbers.length) {
                int new_len = numbers.length * 2;
                int[][] new_numbers = new int[new_len][1];
                System.arraycopy(numbers, 0, new_numbers, 0, numbers.length);
                numbers = new_numbers;
            }

            Scanner scanner = new Scanner(sc.nextLine());
            int col = 0;
            while (scanner.hasNext()) {
                if (col + 1 > numbers[row].length) {
                    int new_len = numbers[row].length * 2;
                    int[] new_row = new int[new_len];
                    System.arraycopy(
                            numbers[row], 0, new_row, 0, numbers[row].length);
                    numbers[row] = new_row;
                }
                numbers[row][col++] = scanner.nextInt();
            }
            if (max_cols < col) {
                max_cols = col;
            }

            // shrink to fit a row
            int[] new_row = new int[col];
            System.arraycopy(numbers[row], 0, new_row, 0, col);
            numbers[row] = new_row;

            row++;
            max_rows++;
        }

        // shrink to fit cols
        int[][] new_numbers = new int[max_rows][1];
        System.arraycopy(numbers, 0, new_numbers, 0, max_rows);
        numbers = new_numbers;

        // used M bytes

        int[] rows = new int[max_rows];
        int[] cols = new int[max_cols];

        // used 3 * M bytes

        for (int i = 0; i < max_rows; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                rows[i] = add_mod(rows[i], abs(numbers[i][j]));
                cols[j] = add_mod(cols[j], abs(numbers[i][j]));
            }
        }
        for (int i = 0; i < max_rows; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                System.out.print(
                        sub_mod(add_mod(rows[i], cols[j]), abs(numbers[i][j])));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

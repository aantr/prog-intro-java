//package org.example.reverse;

import static java.lang.Math.abs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;


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
        MyScanner scanner = new MyScanner(new InputStreamReader(System.in));

        while (scanner.hasNextLine()) {
            MyScanner lineScanner = new MyScanner(scanner.nextLine());
            int col = 0;
            int[] currentRow = new int[1];
            while (lineScanner.hasNext()) {
                if (col >= currentRow.length) {
                    currentRow = Arrays.copyOf(currentRow, currentRow.length * 2);
                }
                currentRow[col++] = lineScanner.next();
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
    }
}

class MyScanner {

    Reader readerIn;
    char[] buffer = new char[1024];
    int currentIndex = 0;
    int currentLength = 0;
    boolean closed = false;

    public MyScanner(InputStreamReader reader) {
        this.readerIn = reader;
    }

    public MyScanner(String reader) {
        readerIn = new StringReader(reader);
    }

    private boolean isValid(char ch) {
        return Character.isDigit(ch);
    }

    private void readBuffer() {
        int res;
        try {
            res = readerIn.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException("IO error while writing to buffer");
        }
        if (res == -1) {
            closed = true;
        } else {
            currentLength = res;
            currentIndex = 0;
        }
    }

    private boolean pushToValid() {

        while (true) {
            boolean was_found = false;
            while (currentIndex < currentLength) {
                if (isValid(buffer[currentIndex])) {
                    was_found = true;
                    break;
                }
                currentIndex++;
            }
            if (was_found) {
                return true;
            }
            readBuffer();
            if (closed) {
                return false;
            }
        }

    }

    private boolean pushToNextLine() {
        while (true) {
            boolean was_found = false;
            if (currentIndex < currentLength) {
                was_found = true;
            }
            if (was_found) {
                return true;
            }
            readBuffer();
            if (closed) {
                return false;
            }
        }

    }

    public boolean hasNext() {
        return pushToValid();
    }

    public boolean hasNextLine() {
        return pushToNextLine();
    }

    public String nextLine() {
        StringBuilder stringBuilder = new StringBuilder();
        while (currentIndex < currentLength) {
            stringBuilder.append(buffer[currentIndex]);
            currentIndex++;
            if (currentIndex == currentLength) {
                readBuffer();
            }
            if (stringBuilder.charAt(stringBuilder.length() - 1) == '\n') {
                break;
            }
        }
        // may throw NumberFormatException
        return stringBuilder.toString();
    }

    public int next() {
        if (!pushToValid()) {
            throw new RuntimeException("Next integer was not found");
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (currentIndex < currentLength && isValid(buffer[currentIndex])) {
            stringBuilder.append(buffer[currentIndex]);
            currentIndex++;
            if (currentIndex == currentLength) {
                readBuffer();
                // may be closed
            }
        }
        // may throw NumberFormatException

        return Integer.parseInt(stringBuilder.toString());
    }

}


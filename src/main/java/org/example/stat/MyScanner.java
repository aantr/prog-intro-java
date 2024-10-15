import javax.lang.model.type.NullType;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class MyScanner {

    public static class ScannerException extends Exception {
        public ScannerException(String msg) {
            super(msg);
        }
    }

    final static int BUF_SIZE = 1024;

    Reader readerIn;
    char[] buffer = new char[BUF_SIZE];
    int currentLineSep = 0;
    int currentIndex = 0;
    int currentLength = 0;
    boolean closed = false;

    public MyScanner(InputStreamReader reader) {
        this.readerIn = reader;
    }

    public MyScanner(String reader) {
        readerIn = new StringReader(reader);
    }

    private static boolean isValidInt(char ch) {
        return Character.isDigit(ch) ||
                Character.getType(ch) == Character.DASH_PUNCTUATION;
    }

    private static boolean isValidIntOct(char ch) {
        return Character.isDigit(ch) ||
                Character.getType(ch) == Character.DASH_PUNCTUATION ||
                Character.toLowerCase(ch) == 'o';
    }

    private static boolean isValidWord(char ch) {
        return Character.isLetter(ch) ||
                Character.getType(ch) == Character.DASH_PUNCTUATION ||
                ch == '\'';
    }

    private static boolean isValidWordCurrency(char ch) {
        return Character.isLetter(ch) ||
                Character.getType(ch) == Character.DASH_PUNCTUATION ||
                ch == '\'' ||
                Character.getType(ch) == Character.CURRENCY_SYMBOL;
    }

    private void readBuffer() throws IOException {
        if (closed) {
            return;
        }
        int res;
        res = readerIn.read(buffer);
        if (res == -1) {
            closed = true;
        } else {
            currentLength = res;
            currentIndex = 0;
        }
    }

    private boolean pushToValid(Function<Character, Boolean> f) throws IOException {
        while (!closed) {
            while (currentIndex < currentLength) {
                if (f.apply(buffer[currentIndex])) {
                    return true;
                }
                currentIndex++;
            }
            readBuffer();
        }
        return false;
    }

    private boolean pushToNextLine() throws IOException {
        while (!closed) {
            if (currentIndex < currentLength) {
                return true;
            }
            readBuffer();
        }
        return false;
    }

    public boolean hasNext(Function<Character, Boolean> f) throws IOException {
        return pushToValid(f);
    }

    public boolean hasNextLine() throws IOException {
        return pushToNextLine();
    }

    public boolean hasNextInt() throws IOException {
        return pushToValid(MyScanner::isValidInt);
    }

    public boolean hasNextWord() throws IOException {
        return pushToValid(MyScanner::isValidWord);
    }

    public boolean hasNextWordCurrency() throws IOException {
        return pushToValid(MyScanner::isValidWordCurrency);
    }

    public Integer nextInt() throws IOException, NumberFormatException, ScannerException {
        return Integer.parseInt(next(MyScanner::isValidInt));
    }

    public String nextWord() throws IOException, ScannerException {
        return next(MyScanner::isValidWord);
    }

    public String nextWordCurrency() throws IOException, ScannerException {
        return next(MyScanner::isValidWordCurrency);
    }

    // IntPredicate
    public String next(Function<Character, Boolean> f) throws IOException, ScannerException {
        if (!pushToValid(f)) { // have a valid char at currentIndex
            throw new ScannerException("Unable to find next int"); // new RuntimeException
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (currentIndex < currentLength && f.apply(buffer[currentIndex])) {
            stringBuilder.append(buffer[currentIndex++]);
            if (currentIndex == currentLength) {
                readBuffer();
            }
        }
        return stringBuilder.toString();
    }

    public String nextLine() throws IOException { // reads until new line sep inclusively
        StringBuilder stringBuilder = new StringBuilder();
        int start = currentIndex;
        while (!closed) {
            if (currentIndex < currentLength) {
                if (readLineSeparator()) {
                    break;
                }
                stringBuilder.append(buffer[currentIndex - 1]);
            } else {
                readBuffer();
            }
        }
        stringBuilder.append(buffer, start, currentIndex - start);

        return stringBuilder.toString();
    }

    // Predicate, WordProcessor
    public void nextLine(Function<Character, Boolean> f, Function<String, Integer> callback) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (!closed) {
            if (currentIndex < currentLength) {
                if (readLineSeparator()) break;
                if (f.apply(buffer[currentIndex - 1])) {
                    stringBuilder.append(buffer[currentIndex - 1]);
                } else {
                    if (!stringBuilder.isEmpty()) {
                        callback.apply(stringBuilder.toString());
                    }
                    stringBuilder = new StringBuilder();
                }
            } else {
                readBuffer();
            }
        }
        if (!stringBuilder.isEmpty()) {
            callback.apply(stringBuilder.toString());
        }
    }

    public int[] nextLineInt() throws IOException {
        final int[][] res = {new int[1]};
        final int[] length = {0};
        nextLine(MyScanner::isValidIntOct, (String s) -> {
            while (length[0] >= res[0].length) {
                res[0] = Arrays.copyOf(res[0], res[0].length * 2);
            }
            String nxt = s.toLowerCase();
            if (nxt.endsWith("o")) {
                res[0][length[0]] = Integer.parseUnsignedInt(nxt.substring(0, nxt.length() - 1), 8);
            } else {
                res[0][length[0]] = Integer.parseInt(nxt);
            }
            length[0]++;
            return 0;
        });
        return Arrays.copyOf(res[0], length[0]);
    }

    public String[] nextLineWord() throws IOException {
        final String[][] res = {new String[1]};
        final int[] length = {0};
        nextLine(MyScanner::isValidWord, (String s) -> {
            while (length[0] >= res[0].length) {
                res[0] = Arrays.copyOf(res[0], res[0].length * 2);
            }
            res[0][length[0]] = s;
            length[0]++;
            return 0;
        });
        return Arrays.copyOf(res[0], length[0]);
    }

    public String[] nextLineWordCurrency() throws IOException {
        final String[][] res = {new String[1]};
        final int[] length = {0};
        nextLine(MyScanner::isValidWordCurrency, (String s) -> {
            while (length[0] >= res[0].length) {
                res[0] = Arrays.copyOf(res[0], res[0].length * 2);
            }
            res[0][length[0]] = s;
            length[0]++;
            return 0;
        });
        return Arrays.copyOf(res[0], length[0]);
    }

    private boolean readLineSeparator() {
        currentIndex++;
        if (buffer[currentIndex - 1] == System.lineSeparator().charAt(currentLineSep)) {
            currentLineSep++;
            if (currentLineSep >= System.lineSeparator().length()) {
                currentLineSep = 0;
                return true;
            }
        } else {
            currentLineSep = 0;
        }
        return false;
    }

    public void close() throws IOException {
        readerIn.close();
        closed = true;
        currentIndex = 0;
        currentLength = 0; // force to read next time
    }
}
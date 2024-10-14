import com.sun.jdi.VoidType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class MyScanner {

    final static int INT_LIMIT = 1024;
    final static int BUF_SIZE = 1024;

    Reader readerIn;
    char[] buffer = new char[BUF_SIZE];
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
        return Character.isDigit(ch) || Character.getType(ch) == Character.DASH_PUNCTUATION;
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

    private void readBuffer() throws IOException {
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
        while (true) {
            boolean was_found = false;
            while (currentIndex < currentLength) {
                if (f.apply(buffer[currentIndex])) {
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

    private boolean pushToNextLine() throws IOException {
        while (true) {
            if (currentIndex < currentLength) {
                return true;
            }
            readBuffer();
            if (closed) {
                return false;
            }
        }
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

    public Integer nextInt() throws IOException, NumberFormatException {
        return Integer.parseInt(next(INT_LIMIT, MyScanner::isValidInt));
    }

    public int nextIntOct() throws IOException, NumberFormatException {
        String nxt = next(INT_LIMIT, MyScanner::isValidIntOct).toLowerCase();
        if (nxt.endsWith("o")) {
            return (int) Long.parseLong(nxt.substring(0, nxt.length() - 1), 8);
        }
        return (int) Long.parseLong(nxt);

    }

    public String nextWord() throws IOException {
        return next(Integer.MAX_VALUE, MyScanner::isValidWord);
    }

    public String next(int limit, Function<Character, Boolean> f) throws IOException {
        if (!pushToValid(f)) {
            throw new RuntimeException("Next was not found");
        }
        StringBuilder stringBuilder = new StringBuilder();
        int start = currentIndex;
        while (currentIndex < currentLength && f.apply(buffer[currentIndex])) {
            if (stringBuilder.length() >= limit) {
                throw new RuntimeException("Next out of limit");
            }
            currentIndex++;
            if (currentIndex == currentLength) {
                stringBuilder.append(buffer, start, currentIndex - start);
                readBuffer();
                start = currentIndex;
                if (!closed) {
                    start = 0;
                }
            }
        }
        stringBuilder.append(buffer, start, currentIndex - start);
        return stringBuilder.toString();
    }

    public String nextLine() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int start = currentIndex;
        while (currentIndex < currentLength) {
            currentIndex++;
            if (buffer[currentIndex - 1] == System.lineSeparator().charAt(0)) {
                break;
            }
            if (currentIndex == currentLength) {
                stringBuilder.append(buffer, start, currentIndex - start);
                readBuffer();
                start = currentIndex;
                if (!closed) {
                    start = 0;
                }
            }
        }
        stringBuilder.append(buffer, start, currentIndex - start);

        return stringBuilder.toString();
    }

    public int[] nextLineInt() throws IOException {
        int[] res = new int[1];
        int length = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while (currentIndex < currentLength) {
            currentIndex++;
            if (buffer[currentIndex - 1] == '\n') {
                break;
            }
            if (MyScanner.isValidInt(buffer[currentIndex - 1])) {
                stringBuilder.append(buffer[currentIndex - 1]);
                if (stringBuilder.length() >= INT_LIMIT) {
                    throw new RuntimeException("Integer is too big");
                }
            } else {
                // append
                if (!stringBuilder.isEmpty()) {
                    while (length >= res.length) {
                        res = Arrays.copyOf(res, res.length * 2);
                    }
                    res[length++] = Integer.parseInt(stringBuilder.toString());
                }
                stringBuilder = new StringBuilder();
            }
            if (currentIndex == currentLength) {
                readBuffer();
            }
        }
        if (!stringBuilder.isEmpty()) {
            while (length >= res.length) {
                res = Arrays.copyOf(res, res.length * 2);
            }
            res[length++] = Integer.parseInt(stringBuilder.toString());
        }
        return Arrays.copyOf(res, length);
    }

    public void close() throws IOException {
        readerIn.close();
        closed = true;
    }

}


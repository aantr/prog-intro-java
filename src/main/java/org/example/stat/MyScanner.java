import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.function.Function;

public class MyScanner {

    final static int INT_LIMIT = 1024;
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
        do {
            while (currentIndex < currentLength) {
                if (f.apply(buffer[currentIndex])) {
                    return true;
                }
                currentIndex++;
            }
            readBuffer();
        } while (!closed);
        return false;
    }

    private boolean pushToNextLine() throws IOException {
        do {
            if (currentIndex < currentLength) {
                return true;
            }
            readBuffer();
        } while (!closed);
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

    public Integer nextInt() throws IOException, NumberFormatException {
        return Integer.parseInt(next(INT_LIMIT, MyScanner::isValidInt));
    }

    public int nextIntOct() throws IOException, NumberFormatException {
        String nxt = next(INT_LIMIT, MyScanner::isValidIntOct).toLowerCase();
        if (nxt.endsWith("o")) {
            return Integer.parseUnsignedInt(nxt.substring(0, nxt.length() - 1), 8);
        }
        return Integer.parseInt(nxt);
    }

    public String nextWord() throws IOException {
        return next(Integer.MAX_VALUE, MyScanner::isValidWord);
    }

    public String next(int limit, Function<Character, Boolean> f) throws IOException {
        if (!pushToValid(f)) { // have a valid char at currentIndex
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

    public String nextLine() throws IOException { // reads until new line sep inclusively
        StringBuilder stringBuilder = new StringBuilder();
        int start = currentIndex;

        do {
            if (currentIndex < currentLength) {
                if (readLineSeparator()) break;
            } else {
                stringBuilder.append(buffer, start, currentIndex - start);
                readBuffer();
                start = currentIndex;
                if (!closed) {
                    start = 0;
                }
            }
        } while (!closed);

        stringBuilder.append(buffer, start, currentIndex - start);

        return stringBuilder.toString();
    }

    private int[] pushNextInt(int[] res, int length, StringBuilder stringBuilder) {
        while (length >= res.length) {
            res = Arrays.copyOf(res, res.length * 2);
        }
        res[length] = Integer.parseInt(stringBuilder.toString());
        return res;
    }

    public int[] nextLineInt() throws IOException {
        int[] res = new int[1];
        int length = 0;
        StringBuilder stringBuilder = new StringBuilder();

        do {
            if (currentIndex < currentLength) {
                if (readLineSeparator()) break;
                if (MyScanner.isValidInt(buffer[currentIndex - 1])) {
                    stringBuilder.append(buffer[currentIndex - 1]);
                    if (stringBuilder.length() >= INT_LIMIT) {
                        throw new RuntimeException("Integer is too big");
                    }
                } else {
                    if (!stringBuilder.isEmpty()) {
                        res = pushNextInt(res, length++, stringBuilder);
                    }
                    stringBuilder = new StringBuilder();
                }
            } else {
                readBuffer();
            }
        } while (!closed);

        if (!stringBuilder.isEmpty()) {
            res = pushNextInt(res, length++, stringBuilder);
        }
        return Arrays.copyOf(res, length);
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

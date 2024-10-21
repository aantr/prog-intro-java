package myscanner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.function.Function;

public class MyScanner {

    public static class ScannerException extends Exception {
        public ScannerException(String msg) {
            super(msg);
        }
    }

    // MyArray(Integer.class) or MyArray(String.class)
    private static class MyArray {
        Object[] arr;
        int length;

        public MyArray(Class<?> type) {
            if (type == Integer.class) {
                arr = new Integer[1];
            } else if (type == String.class) {
                arr = new String[1];
            } else {
                arr = new Object[1];
            }
            length = 0;
        }

        public void add(Object el) {
            while (arr.length <= length) {
                arr = Arrays.copyOf(arr, arr.length * 2);
            }
            arr[length] = el;
            length++;
        }

        public Object get(int idx) {
            if (idx < 0 || idx >= length) {
                throw new ArrayIndexOutOfBoundsException("MyArray.get index of bounds");
            }
            return arr[idx];
        }

        public Object[] getArray() {
            return Arrays.copyOf(arr, length);
        }
    }

    private final static int BUF_SIZE = 1024;

    private final Reader readerIn;
    private final char[] buffer = new char[BUF_SIZE];
    private int currentLineSep = 0;
    private int currentIndex = 0;
    private int currentLength = 0;
    private boolean closed = false;

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

    private static Integer parseIntOct(String s) {
        String nxt = s.toLowerCase();
        if (nxt.endsWith("o")) {
            return Integer.parseUnsignedInt(nxt.substring(0, nxt.length() - 1), 8);
        }
        return Integer.parseInt(nxt);
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

    // Predicate x
    private boolean pushToValid(Function<Character, Boolean> f) throws IOException { // moves currentIndex to nearest ok predicate symbol and sets the wasLineSeparator
        while (!closed) {
            if (currentIndex < currentLength) {
                if (f.apply(buffer[currentIndex])) {
                    return true;
                }
                currentIndex++;
            } else {
                readBuffer();
            }
        }
        return false;
    }

    // Predicate x
    // return true if there was a line break between current pos and next input
    private boolean pushToLineSeparator(Function<Character, Boolean> f) throws IOException { // moves currentIndex to either nearest ok predicate symbol or line sep
        while (!closed) {
            if (currentIndex < currentLength) {
                if (f.apply(buffer[currentIndex])) {
                    return false;
                }
                if (readLineSeparator()) {
                    return true;
                }
            } else {
                readBuffer();
            }
        }
        return false;
    }

    private boolean pushToNextLine() throws IOException {
        while (!closed) {
            if (currentIndex < currentLength) {
                return true;
            } else {
                readBuffer();
            }
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

    public boolean hasNextIntOct() throws IOException {
        return pushToValid(MyScanner::isValidIntOct);
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

    public Integer nextIntOct() throws IOException, NumberFormatException, ScannerException {
        return parseIntOct(next(MyScanner::isValidIntOct));
    }

    public String nextWord() throws IOException, ScannerException {
        return next(MyScanner::isValidWord);
    }

    public String nextWordCurrency() throws IOException, ScannerException {
        return next(MyScanner::isValidWordCurrency);
    }

    // IntPredicate
    private String next(Function<Character, Boolean> f) throws IOException, ScannerException {
        if (!pushToValid(f)) { // has a valid char at currentIndex
            throw new ScannerException("Unable to find next");
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

    private boolean hasNextLineSeparator(Function<Character, Boolean> f) throws IOException {
        return pushToLineSeparator(f);
    }

    public boolean hasNextLineSeparatorInt() throws IOException {
        return hasNextLineSeparator(MyScanner::isValidInt);
    }

    public boolean hasNextLineSeparatorIntOct() throws IOException {
        return hasNextLineSeparator(MyScanner::isValidIntOct);
    }

    public boolean hasNextLineSeparatorWord() throws IOException {
        return hasNextLineSeparator(MyScanner::isValidWord);
    }

    public boolean hasNextLineSeparatorWordCurrency() throws IOException {
        return hasNextLineSeparator(MyScanner::isValidWordCurrency);
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
    private void nextLineRead(Function<Character, Boolean> f, Function<String, Void> callback) throws IOException {
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

    private Object[] nextLine(Function<Character, Boolean> f, Function<String, Object> callback, Class<?> type) throws IOException {
        final MyArray myArray = new MyArray(type);
        nextLineRead(f, (String s) -> {
            myArray.add(callback.apply(s));
            return null;
        });
        return myArray.getArray();
    }

    public Integer[] nextLineInt() throws IOException {
        return (Integer[]) nextLine(MyScanner::isValidInt, Integer::parseInt, Integer.class);
    }

    public Integer[] nextLineIntOct() throws IOException {
        return (Integer[]) nextLine(MyScanner::isValidIntOct, MyScanner::parseIntOct, Integer.class);
    }

    public String[] nextLineWord() throws IOException {
        return (String[]) nextLine(MyScanner::isValidWord, (String s) -> s, String.class);
    }

    public String[] nextLineWordCurrency() throws IOException {
        return (String[]) nextLine(MyScanner::isValidWordCurrency, (String s) -> s, String.class);
    }

    private boolean readLineSeparator() { // moves currentIndex and returns if there was a line break
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

    public boolean isClosed() {
        return closed;
    }
}
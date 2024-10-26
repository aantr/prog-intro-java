package myscanner;

import java.io.IOException;
import java.io.Reader;

public class MyScanner implements AutoCloseable {
    private final static int BUF_SIZE = 1024;

    private final Reader readerIn;
    private final char[] buffer = new char[BUF_SIZE];
    private int bufferIndex;
    private int bufferLength;
    private boolean closed;
    private boolean eof;

    private final PrefixFunction prefixFunction;

    private int readChars;
    private int currentPrefix;
    private int currentPosition;
    private int nextPrefix;
    private int prevPrefixFunction;
    private boolean wasLineSeparator;
    private boolean hasNextSymbol;
    private char nextSymbol;

    @FunctionalInterface
    public interface Predicate {
        boolean apply(char x);
    }

    public MyScanner(Reader reader, String lineSeparator) {
        this.readerIn = reader;
        prefixFunction = new PrefixFunction(lineSeparator).build(); // might be static
    }

    public MyScanner(Reader reader) {
        this(reader, System.lineSeparator());
    }

    // writes next char to bufferIndex
    // returns true if nothing to read
    private boolean nextChar() throws IOException {
        while (!closed) {
            if (bufferIndex < bufferLength) {
                return false;
            } else {
                bufferIndex = 0;
                if ((bufferLength = readerIn.read(buffer)) == -1) {
                    closed = true;
                }
            }
        }
        return true;
    }

    private void setNullState() {
        readChars = 0;
        prevPrefixFunction = 0;
        currentPrefix = 0;
        currentPosition = 0;
        nextPrefix = 0;
    }

    private char setLineSep() {
        setNullState();
        wasLineSeparator = true;
        return 0;
    }

    private char setEof() {
        eof = true;
        return 0;
    }

    // if stream is closed but there is a prefix of a read line sep return it else return eof.
    private char onEof() {
        if (currentPrefix <= currentPosition) {
            return setEof();
        }
        nextPrefix = 0;
        readChars--;
        return prefixFunction.str.charAt(currentPosition++);
    }

    private char readChar() {
        setNullState();
        return buffer[bufferIndex - 1];
    }

    private char findNextPrefix() throws IOException {
        while (readChars <= nextPrefix) { // find next
            currentPrefix = nextPrefix; // set maximum length of prefix of line sep that char in
            if (nextPrefix == prefixFunction.str.length()) { // it is a line sep
                return setLineSep();
            }
            if (nextChar()) { // next was not found because of eof
                return onEof();
            }
            // read next char and set nextPrefix as a maximum prefix of
            // a line sep that equals current suffix
            nextPrefix = readLineSeparator(bufferIndex++);
            readChars++;
        }
        readChars--;
        if (currentPosition < currentPrefix) {
            return prefixFunction.str.charAt(currentPosition++);
        }
        return readChar();
    }

    private char readNextOrSeparator() throws IOException {
        if (hasNextSymbol) {
            hasNextSymbol = false;
            return nextSymbol;
        }
        wasLineSeparator = false;
        if (readChars == 0 && closed) {
            return setEof();
        }
        // possible states are:
        // 1) null state
        // 2) next char is not a line sep but in prefix of line sep, may be started earlier
        // 3) next char is not a line sep and not in prefix of any line sep
        // 4) next char is in prefix of a line sep but might be a line sep
        if (readChars > nextPrefix) {
            readChars--;
            if (currentPosition < currentPrefix) { // 2
                return prefixFunction.str.charAt(currentPosition++);
            }
            return readChar(); // 3
        }
        currentPrefix = nextPrefix;
        currentPosition = 0;
        return findNextPrefix(); // 4
    }

    // returns maximum suffix with current input, s.t. suffix = lineSep prefix (see PrefixFunction)
    // if lineSep appeared, then we set prevPrefixFunctionValue equals 0, so lineSeps don't intersect each other
    private int readLineSeparator(int index) {
        return prevPrefixFunction = prefixFunction.get(prevPrefixFunction, buffer[index]);
    }

    // has unread chars before eof
    public boolean hasNextLine() throws IOException {
        return hasNextOrSeparator((char s) -> true);
    }

    // read until valid or lineSep or eof, if found either lineSep or valid then save it
    public boolean hasNextOrSeparator(Predicate f) throws IOException {
        while (true) {
            char ch = readNextOrSeparator();
            if (eof) {
                return false;
            }
            if (wasLineSeparator || f.apply(ch)) {
                // don`t skip current char
                setNextChar(ch);
                return true;
            }
        }
    }

    private void setNextChar(char ch) {
        nextSymbol = ch;
        hasNextSymbol = true;
    }

    // returns empty string if read line sep
    // return string contains all next consecutive symbols applied for predicate
    public String nextOrSeparator(Predicate f) throws IOException, ScannerException {
        if (!hasNextOrSeparator(f)) {
            throw new ScannerException("Unable to find either next or separator");
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            char ch = readNextOrSeparator();
            if (eof) {
                break;
            }
            // if lineSep at the end of the word or not valid then set next char as current
            // so after reading a word scanner does not skip next char
            boolean valid = f.apply(ch);
            if (wasLineSeparator || !valid) {
                if (!stringBuilder.isEmpty()) {

                    setNextChar(ch);
                }
                break;
            }
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    public void close() throws IOException {
        readerIn.close();
        closed = true;
    }

    // static methods

    public static boolean isValidInt(char ch) {
        return Character.isDigit(ch) ||
                ch == '-';
    }

    public static boolean isValidIntOct(char ch) {
        return Character.isDigit(ch) ||
                ch == '-' ||
                Character.toLowerCase(ch) == 'o';
    }

    public static int parseIntOct(String s) {
        if (s.endsWith("o") || s.endsWith("O")) {
            return Integer.parseUnsignedInt(s.substring(0, s.length() - 1), 8);
        }
        return Integer.parseInt(s);
    }

    public static boolean isValidWord(char ch) {
        return Character.isLetter(ch) ||
                Character.getType(ch) == Character.DASH_PUNCTUATION ||
                ch == '\'';
    }

    public static boolean isValidWordCurrency(char ch) {
        return Character.isLetter(ch) ||
                Character.getType(ch) == Character.DASH_PUNCTUATION ||
                Character.getType(ch) == Character.CURRENCY_SYMBOL ||
                ch == '\'';
    }

}
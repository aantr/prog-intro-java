//package wordStat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class MyScanner implements AutoCloseable {
    private final static int BUF_SIZE = 1024;

    private final Reader readerIn;
    private final char[] buffer = new char[BUF_SIZE];
    private int currentIndex = 0;
    private int currentLength = 0;
    private boolean closed = false;

    final private static PrefixFunction prefixFunction = new PrefixFunction(System.lineSeparator()).build();
    private int currentLineSeparator = 0;
    private int currentLineSeparatorPosition = 0;
    private int nextLineSeparator = 0;
    private int nextLineSeparatorPosition = 0;
    private int prevPrefix = 0;
    private int distance = 0;
    private boolean wasLineSep = false;

    @FunctionalInterface
    public interface Predicate {
        boolean apply(char x);
    }

    @FunctionalInterface
    public interface WordProcessor {
        void apply(String x);
    }

    public MyScanner(InputStreamReader reader) {
        this.readerIn = reader;
    }

    public MyScanner(String reader) {
        readerIn = new StringReader(reader);
    }

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
                ch == '\'' ||
                Character.getType(ch) == Character.CURRENCY_SYMBOL;
    }

    private void readBuffer() throws IOException {
        if (closed) {
            return;
        }
        int res = readerIn.read(buffer);
        if (res == -1) {
            closed = true;
        } else {
            currentLength = res;
            currentIndex = 0;
        }
    }

    private boolean readNextChar() throws IOException {
        while (!closed) {
            if (currentIndex < currentLength) {
                return true;
            } else {
                readBuffer();
            }
        }
        return false;
    }

    private char goFromNormal() throws IOException {
        while (nextLineSeparator > distance) {
            if (nextLineSeparator == System.lineSeparator().length()) {
                currentLineSeparator = 0;
                currentLineSeparatorPosition = 0;
                nextLineSeparator = 0;
                nextLineSeparatorPosition = 0;
                wasLineSep = true;
                return 0;
            }
            // read next Char
            if (!readNextChar()) {
                return 0;
            }
            int length = readLineSeparator(currentIndex);
            nextLineSeparatorPosition = 0;
            nextLineSeparator = length;
            currentIndex++;
            distance++;
        }
        return System.lineSeparator().charAt(currentLineSeparatorPosition++);
    }


    // want symbol that not in linesep or linesep
    // determine current symbol or linesep
    // update currentLineSeparator
    private char readNextOrLineSeparator() throws IOException {
        if (currentLineSeparatorPosition < currentLineSeparator) {
            if (distance >= nextLineSeparator) {
                distance--;
                return System.lineSeparator().charAt(currentLineSeparatorPosition++);
            }
            // distance == nextLineSeparator - 1
            // now replace
            currentLineSeparator = nextLineSeparator;
            currentLineSeparatorPosition = nextLineSeparatorPosition;
            // and go
            char res = goFromNormal();
            return res;
        }
        // suffix is null
        if (!readNextChar()) { // closed == true
            return 0;
        }
        if ((currentLineSeparator = readLineSeparator(currentIndex)) == 0) { // save null state
            return buffer[currentIndex++];
        }
        currentIndex++; // !
        currentLineSeparatorPosition = currentLineSeparator - 1;

        nextLineSeparator = currentLineSeparator;
        nextLineSeparatorPosition = currentLineSeparatorPosition;
        distance = 0;
//        System.err.println(currentLineSeparatorPosition);
        char res = goFromNormal();
//        if (res > 0) System.err.println("char: " + (int) res);
        return res;
    }

    // returns maximum suffix with current input st suffix = linesep prefix
    private int readLineSeparator(int index) {
        return prevPrefix = prefixFunction.get(prevPrefix, buffer[index]);
    }

    public boolean hasNextLine() throws IOException {
        return readNextChar();
    }

    // returns empty string if read line sep
    // return string contains all next consecutive symbols applied for predicate
    public String nextNextOrLineSeparator(Predicate f) throws IOException, ScannerException {
        StringBuilder stringBuilder = new StringBuilder();

        while (true) {
            wasLineSep = false;
            char ch = readNextOrLineSeparator();
//            System.err.println(ch + " " + closed);
            if (closed) {
                throw new ScannerException("Unable to find next");
            }
            if (wasLineSep) {
                return "";
            }
            if (f.apply(ch)) {
                stringBuilder.append(ch);
                break;
            }
        }

        while (true) {
            wasLineSep = false;
            char ch = readNextOrLineSeparator();
            if (closed) {
                break;
            }
            boolean predicateRes = f.apply(ch);
            boolean sepAndFullPredicate = wasLineSep && isLineSeparatorInPredicate(f);
            if (!wasLineSep && predicateRes || sepAndFullPredicate) {
                stringBuilder.append(ch);
            }
            if (wasLineSep && !sepAndFullPredicate || !predicateRes) {
                // todo: remember last symbol ch for readNextOrLineSeparator
                break;
            }
        }
        return stringBuilder.toString();
    }

    private boolean isLineSeparatorInPredicate(Predicate f) {
        for (int i = 0; i < System.lineSeparator().length(); i++) {
            if (!f.apply(System.lineSeparator().charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean wasLineSeparator() throws IOException {
        return wasLineSep;
    }

    public void close() throws IOException {
        readerIn.close();
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

}

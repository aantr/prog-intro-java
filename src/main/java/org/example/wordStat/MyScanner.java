import java.io.IOException;
import java.io.Reader;

public class MyScanner implements AutoCloseable {
    private final static int BUF_SIZE = 1024;

    private final Reader readerIn;
    private final char[] buffer = new char[BUF_SIZE];
    private int currentIndex = 0;
    private int currentLength = 0;
    private boolean closed = false;
    private boolean eof = false;

    private final PrefixFunction prefixFunction;

    private int currentLineSeparator = 0;
    private int currentLineSeparatorPosition = 0;
    private int nextLineSeparator = 0;
    private int nextLineSeparatorPosition = 0;
    private int prevPrefixFunctionValue = 0;
    private int distance = 0;
    private boolean wasLineSep = false;
    private boolean wasPrevSymbol = false;
    private char prevSymbol = 0;

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


    private void printState() {
        System.err.println("cur pos: " + currentLineSeparatorPosition + " cur len: " + currentLineSeparator +
                " nxt len: " + nextLineSeparator + " dist: " + distance);
    }

    // read symbol from null or current set.
    // set next, current either 0 or > 0
    private char goFromNormal() throws IOException {
        // what about oef ?
//        printState();
        if (currentLineSeparator == 0) {

            assert nextLineSeparator == 0 && currentLineSeparatorPosition == 0 && nextLineSeparatorPosition == 0;
            while (distance <= nextLineSeparator) { // find nxt

                currentLineSeparator = nextLineSeparator; // set last correct line sep

                if (nextLineSeparator == prefixFunction.str.length()) {
//                    System.err.println("/n");
                    currentLineSeparator = 0;
                    currentLineSeparatorPosition = 0;
                    nextLineSeparator = 0;
                    nextLineSeparatorPosition = 0;
                    wasLineSep = true;
                    distance = 0;
                    prevPrefixFunctionValue = 0;
                    return 0;
                }

                if (!readNextChar()) { // might not be correct nxt
                    if (currentLineSeparator <= currentLineSeparatorPosition) {
                        eof = true;
                        return 0; // eof
                    } else {
                        nextLineSeparator = 0;
                        nextLineSeparatorPosition = 0;
                        distance--;
//                        System.err.println("not eof 0: " + prefixFunction.str.charAt(currentLineSeparatorPosition));
                        return prefixFunction.str.charAt(currentLineSeparatorPosition++);
                    }
                }

                int length = readLineSeparator(currentIndex++); // if length == 0 then go null
                nextLineSeparatorPosition = 0;
                nextLineSeparator = length;

                distance++;
            }
            distance--;
            if (currentLineSeparatorPosition < currentLineSeparator) {
                return prefixFunction.str.charAt(currentLineSeparatorPosition++);
            }
            return buffer[currentIndex - 1];

        }
        // current line sep > 0
        // nxt was not set yet

        assert nextLineSeparator == currentLineSeparator;
        assert(currentLineSeparatorPosition == 0 && currentLineSeparator > 0);
//        System.err.println("pos: " + currentLineSeparatorPosition);
        while (distance <= nextLineSeparator) { // find nxt

            currentLineSeparator = nextLineSeparator; // set last correct line sep

            if (nextLineSeparator == prefixFunction.str.length()) {
                currentLineSeparator = 0;
                currentLineSeparatorPosition = 0;
                nextLineSeparator = 0;
                nextLineSeparatorPosition = 0;
                wasLineSep = true;
                distance = 0;
                prevPrefixFunctionValue = 0;

                return 0;
            }

            if (!readNextChar()) {
                if (currentLineSeparator <= currentLineSeparatorPosition) {
                    eof = true;
//                    System.err.println("eof");
                    return 0; // eof
                } else {
                    nextLineSeparatorPosition = 0;
                    nextLineSeparator = 0;
                    distance--;
//                    System.err.println("not eof: " + prefixFunction.str.charAt(currentLineSeparatorPosition));

                    return prefixFunction.str.charAt(currentLineSeparatorPosition++);
                }
            }

            int length = readLineSeparator(currentIndex++);
            nextLineSeparatorPosition = 0;
            nextLineSeparator = length;

            distance++;
        }
        distance--;
        return prefixFunction.str.charAt(currentLineSeparatorPosition++);
    }

    // want symbol that not in lineSep or lineSep
    // determine current symbol or lineSep
    // update currentLineSeparator
    private char readNextOrLineSeparator() throws IOException {
        if (wasPrevSymbol) {
            wasPrevSymbol = false;
//            System.err.println("res 0: " + prevSymbol);
            return prevSymbol;
        }
        wasLineSep = false;
        if (distance > 0) {
            if (distance > nextLineSeparator) {
                distance--;
                if (currentLineSeparatorPosition < currentLineSeparator) {
//                    printState();;
//                    System.err.println("res eof: " + prefixFunction.str.charAt(currentLineSeparatorPosition));

                    return prefixFunction.str.charAt(currentLineSeparatorPosition++);
                }
//                System.err.println("res buffer: " + buffer[currentIndex - 1]);
                currentLineSeparator = 0;
                currentLineSeparatorPosition = 0;
                assert nextLineSeparator == 0 && nextLineSeparatorPosition == 0; // go to null state
                return buffer[currentIndex - 1];
            }
            // in next ?

            // distance == nextLineSeparator
            // now replace
            currentLineSeparator = nextLineSeparator;
            currentLineSeparatorPosition = nextLineSeparatorPosition;
            // and go
            char res = goFromNormal();
//            System.err.println("res 1: " + res);

            return res;
        }
        // current_pos = currentLength
        // suffix is null
//        printState();
        assert currentLineSeparatorPosition == currentLineSeparator;
//        currentLineSeparator = 0;
//        currentLineSeparatorPosition = 0;
        if (closed) {
            eof = true;
            return 0;
        }
        char res = goFromNormal();
//        System.err.println("res 3: " + res);
        return res;
    }

    // returns maximum suffix with current input, s.t. suffix = lineSep prefix (see PrefixFunction)
    private int readLineSeparator(int index) {
        return prevPrefixFunctionValue = prefixFunction.get(prevPrefixFunctionValue, buffer[index]);
    }

    // alternative name: has unread chars before eof
    public boolean hasNextLine() throws IOException {
        return hasNextOrLineSeparator((char s) -> true);
    }

    // read until valid or lineSep or eof, if found either lineSep or valid then save it
    public boolean hasNextOrLineSeparator(Predicate f) throws IOException {
        while (true) {
            char ch = readNextOrLineSeparator();
            if (eof) {
                return false;
            }
            if (wasLineSep || f.apply(ch)) {
                prevSymbol = ch;
                wasPrevSymbol = true;
                return true;
            }
        }
    }

    // returns empty string if read line sep
    // return string contains all next consecutive symbols applied for predicate
    public String nextOrLineSeparator(Predicate f) throws IOException, ScannerException {
        if (!hasNextOrLineSeparator(f)) { // reads until valid
            throw new ScannerException("Unable to find either next or lineSep");
        }
        // called hasNextOrLineSeparator here, so we have been prevSymbol
        // if it is lineSep then return lineSep
        // else return char

        StringBuilder stringBuilder = new StringBuilder();

        while (true) {
            char ch = readNextOrLineSeparator();
//            System.err.println("sym: " + ch);
            if (eof) { // has at least one
                break;
            }

            if (wasLineSep || !f.apply(ch)) {
                if (!stringBuilder.isEmpty()) {
                    wasPrevSymbol = true;
                    prevSymbol = ch;
                }
                break;
            }
            if (f.apply(ch)) {
                stringBuilder.append(ch);
            }
        }

        return stringBuilder.toString();
    }

    public void close() throws IOException {
        readerIn.close();
        closed = true;
    }

}

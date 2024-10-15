//package org.example.stat;

import java.io.*;
import java.util.*;
import static java.lang.Character.isWhitespace;

public class MineScanner implements AutoCloseable {
    private final Reader in;
    private final char[] buffer = new char[1 << 27];
    private long last = 0;
    private long current = 0;
    private final char[] chars = new char[]{'\''};

    public MineScanner(InputStream source) throws IOException {
        in = new InputStreamReader(source);
        readBuffer();
    }

    public MineScanner(InputStreamReader source) throws IOException {
        in = source;
        readBuffer();
    }

    public MineScanner(String input) throws IOException {
        in = new StringReader(input);
        readBuffer();
    }

    private void readBuffer() throws IOException {
        last = in.read(buffer);
        current = 0;
    }

    private boolean isPartOfWord(char ch) {
        boolean good = Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch);
        for (var chr: chars) { // auto in c++
            if (ch == chr) {
                good = true;
                break;
            }
        }
        return good;
    }

    private boolean isPartOfDigit(char ch) {
        return Character.isDigit(ch) || ch == '-';
    }

    public boolean hasNextLine() throws IOException {
        while (true) {
            if (current >= last) {
                readBuffer();
                if (last == -1) {
                    return false;
                }
            }
            while (current < last && isWhitespace(buffer[(int) current])
                    && buffer[(int) current] != System.lineSeparator().charAt(0)) {
                current++;
            }
            if (current < last || buffer[(int) current] == System.lineSeparator().charAt(0)) {
                return true;
            }
        }
    }

    public boolean hasNextInt() throws IOException {
        while (true) {
            if (current >= last) {
                readBuffer();
                if (last == -1) {
                    return false;
                }
            }
            while (current < last && !isPartOfDigit(buffer[(int) current])) {
                current++;
            }
            return current < last || isPartOfDigit(buffer[(int) current]);
        }
    }

    public boolean hasNext() throws IOException {
        while (true) {
            if (current == last) {
                readBuffer();
                if (last == -1) {
                    return false;
                }
            }
            while (current < last && isWhitespace(buffer[(int) current])
                    && buffer[(int) current] != System.lineSeparator().charAt(0)) {
                current++;
            }
            if (current < last && buffer[(int) current] != System.lineSeparator().charAt(0)) {
                return true;
            }
            if (current < last && buffer[(int) current] == System.lineSeparator().charAt(0)) {
                current++;
            }
        }
    }

    public String next() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (true) {
            if (current == last) {
                readBuffer();
                if (last == -1) {
                    return !builder.isEmpty() ? builder.toString() : null;
                }
            }
            while (current < last && (isWhitespace(buffer[(int) current])
                    || buffer[(int) current] == System.lineSeparator().charAt(0))) {
                if (!builder.isEmpty()) {
                    return builder.toString();
                }
                current++;
            }
            if (current < last) {
                builder.append(buffer[(int) current++]);
            }
        }
    }

    public String nextLine() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (true) {
            if (current >= last) {
                readBuffer();
            }
            if (last == -1) {
                break;
            }
            if (buffer[(int) current] == System.lineSeparator().charAt(0)) {
                current++;
                break;
            }
            builder.append(buffer[(int) current]);
            current++;
        }
        return builder.toString();
    }


    public String[] nextLineAllWords() throws IOException {
        String[] words = new String[1];
        int wordsCount = 0;
        StringBuilder word = new StringBuilder();

        while (true) {
            if (current == last) {
                readBuffer();
                if (last == -1) {
                    break;
                }
            }
            while (current < last && (buffer[(int) current] == System.lineSeparator().charAt(0) ||
                    (!isPartOfWord(buffer[(int) current]) && !word.isEmpty()))) {
                if (!word.isEmpty()) {
                    if (words.length <= wordsCount) {
                        words = Arrays.copyOf(words, words.length * 2);
                    }
                    words[wordsCount++] = word.toString();
                    word.setLength(0);
                }
                if (buffer[(int) current] == System.lineSeparator().charAt(0)) {
                    current++;
                    return Arrays.copyOf(words, wordsCount);
                }
                current++;
            }
            if (current < last && isPartOfWord(buffer[(int) current])) {
                word.append(buffer[(int) current++]);
            } else {
                current++;
            }
        }
        if (!word.isEmpty()) {
            if (words.length <= wordsCount) {
                words = Arrays.copyOf(words, words.length * 2);
            }
            words[wordsCount++] = word.toString();
        }
        return Arrays.copyOf(words, wordsCount);
    }

    public int[] nextLineAllInts() throws IOException {
        int[] allInts = new int[1];
        int count = 0;
        StringBuilder number = new StringBuilder();

        while (true) {
            if (current == last) {
                readBuffer();
                if (last == -1) {
                    break;
                }
            }
            while (current < last &&
                    (buffer[(int) current] == System.lineSeparator().charAt(0) ||
                            (!isPartOfDigit(buffer[(int) current]) && !number.isEmpty()))) {
                if (!number.isEmpty()) {
                    if (allInts.length <= count) {
                        allInts = Arrays.copyOf(allInts, allInts.length * 2);
                    }
                    allInts[count++] = Integer.parseInt(number.toString());
                    number.setLength(0);
                }
                if (buffer[(int) current] == System.lineSeparator().charAt(0)) {
                    current++;
                    return Arrays.copyOf(allInts, count);
                }
                current++;
            }
            if (current < last && isPartOfDigit(buffer[(int) current])) {
                number.append(buffer[(int) current++]);
            } else {
                current++;
            }
        }
        if (!number.isEmpty()) {
            if (allInts.length <= count) {
                allInts = Arrays.copyOf(allInts, allInts.length * 2);
            }
            allInts[count++] = Integer.parseInt(number.toString());
        }
        return Arrays.copyOf(allInts, count);
    }

    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.out.println("Error while closing the scanner: " + e.getMessage());
        }
    }
}

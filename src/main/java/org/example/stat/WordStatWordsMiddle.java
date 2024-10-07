//package org.example.stat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;


class MyScanner {

    Reader readerIn;
    char[] buffer;
    int currentIndex = 0;
    int currentLength = 0;
    boolean closed = false;

    public MyScanner(InputStreamReader reader) {
        readerIn = reader;
        buffer = new char[1024];
    }

    public MyScanner(String reader) {
        readerIn = new StringReader(reader);
    }

    private boolean isValid(char ch) {
        return Character.isLetter(ch) ||
                Character.getType(ch) == Character.DASH_PUNCTUATION ||
                ch == '\'';
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

    public boolean hasNext() {
        return pushToValid();
    }

    public String next() {
        if (!pushToValid()) {
            throw new RuntimeException("Next string was not found");
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (currentIndex < currentLength && isValid(buffer[currentIndex])) {
            stringBuilder.append(buffer[currentIndex]);
            currentIndex++;
            if (currentIndex == currentLength) {
                readBuffer();
            }
        }
        return stringBuilder.toString();
    }

}


public class WordStatWordsMiddle {

    private final static int MAX_N = 100000;

    public static class StringReverseComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) { // reverse comparator
            return -1 * o1.compareTo(o2);
        }
    }
    
    public static void main(String[] args) {
        try {
            String filename_in = args[0], filename_out = args[1];
            MyScanner scanner = new MyScanner(new InputStreamReader(new FileInputStream(filename_in), StandardCharsets.UTF_8));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename_out), StandardCharsets.UTF_8);

            String[] occurrences = new String[MAX_N];
            int all = 0;
            while (scanner.hasNext()) {
                String str = scanner.next().toLowerCase();
                if (str.length() > 6) {
                    str = str.substring(3, str.length() - 3);
                }
                occurrences[all] = str;
                all++;
            }
            occurrences = Arrays.copyOf(occurrences, all);
            Arrays.sort(occurrences, new StringReverseComparator());
            for (int i = 0; i < all; i++) {
                int j = i;
                while (j < all && Objects.equals(occurrences[i], occurrences[j])) j++;
                writer.write(occurrences[i] + " " + (j - i) + "\n");
                i = j - 1;
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

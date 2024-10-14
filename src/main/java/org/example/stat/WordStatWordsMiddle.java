import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class WordStatWordsMiddle {

    public static class StringReverseComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) { // reverse comparator
            return -1 * o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        String filename_in = args[0], filename_out = args[1];
        try {
            MyScanner scanner = new MyScanner(new InputStreamReader(new FileInputStream(filename_in), StandardCharsets.UTF_8));
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename_out), StandardCharsets.UTF_8);
                try {
                    String[] occurrences = new String[1];
                    int all = 0;
                    while (scanner.hasNextWord()) {
                        String str = scanner.nextWord().toLowerCase();
                        if (str.length() > 6) {
                            str = str.substring(3, str.length() - 3);
                        }
                        if (occurrences.length <= all) {
                            occurrences = Arrays.copyOf(occurrences, occurrences.length * 2);
                        }
                        occurrences[all] = str;
                        all++;
                    }
                    occurrences = Arrays.copyOf(occurrences, all);
                    Arrays.sort(occurrences, new StringReverseComparator());
                    for (int start = 0; start < all; start++) {
                        int last = start;
                        while (last < all && Objects.equals(occurrences[start], occurrences[last])) {
                            last++;
                        }
                        writer.write(occurrences[start] + " " + (last - start) + System.lineSeparator());
                        start = last - 1;
                    }
                } finally {
                    writer.close();
                }
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        }
    }
}

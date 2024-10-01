package org.example.stat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public class WordsShingles {
    static class Pair {
        String first;
        int second;
        Pair (String first, int second) {
            this.first = first;
            this.second = second;
        }

        public boolean less(Pair other) {
            return first.compareTo(other.first) < 0 || first.compareTo(other.first) == 0 && second < other.second;
        }
    }


    private final static int MAXN = 100000;

    private static void sort(Pair[] array, int[] idx, int low, int high) {
        if (low >= high || low < 0 || high > array.length) {
            return;
        }
        int middle = (high + low) / 2;
        int i = low, j = high - 1;
        while (i <= j) {
            while (array[i].less(array[middle])) {
                i++;
            }
            while (array[middle].less(array[j])) {
                j--;
            }
            if (i <= j) {
                int temp_idx = idx[array[i].second];
                idx[array[i].second] = idx[array[j].second];
                idx[array[j].second] = temp_idx;
                Pair temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        sort(array, idx, low, j);
        sort(array, idx, i, high);
    }

    public static void main(String[] args) throws IOException {
        String filename_in = args[0], filename_out = args[1];
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename_in), StandardCharsets.UTF_8);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename_out), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        Pair[] occurrences = new Pair[MAXN];
        int[] idx = new int[MAXN];
        int all = 0;
        int ch;
        while ((ch = reader.read()) != -1) {
            if (!Character.isLetter(ch) &&
                    Character.getType(ch) != Character.DASH_PUNCTUATION &&
                    ch != '\'') {
                if (!sb.isEmpty()) {
                    String str = sb.toString().toLowerCase();
                    sb = new StringBuilder();

                    // add
                    occurrences[all] = new Pair(str, all);
                    idx[all] = all;
                    all++;
                }
            } else {
                sb.append((char) ch);
            }
        }
        sort(occurrences, idx, 0, all);

        for (int i = 0; i < all; i++) {
            int j = i;
            while (j < all && Objects.equals(occurrences[i].first, occurrences[j].first)) j++;
            writer.write(occurrences[i].first + " " + (j - i) + "\n");
            i = j - 1;
        }
        writer.close();
        reader.close();

    }
}

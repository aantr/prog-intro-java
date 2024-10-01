//package org.example.stat;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class WordStatInput {
    private final static int MAXN = 1000;

    private static int hashCode(String str) {
        int res = 0;
        int p = 1;
        for (int i = 0; i < str.length(); i++) {
            res += p * str.charAt(i);
            p *= 101;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        String filename_in = args[0], filename_out = args[1];
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename_in), StandardCharsets.UTF_8);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename_out),  StandardCharsets.UTF_8);
        int ch;
        StringBuilder sb = new StringBuilder();
        HashMap<Integer, Integer> idx = new HashMap<>();
        int[] cnt = new int[MAXN];
        String[] word = new String[MAXN];
        int word_count = 0;
        while ((ch = reader.read()) != -1) {
            if (!Character.isLetter(ch) &&
                    Character.getType(ch) != Character.DASH_PUNCTUATION &&
                    ch != '\'') {
                if (!sb.isEmpty()) {
                    String str = sb.toString().toLowerCase();
                    int hc = hashCode(str);
                    if (!idx.containsKey(hc)) {
                        word[word_count] = str;
                        idx.put(hc, word_count++);
                    }
                    int value = idx.get(hc);
                    cnt[value]++;
                    sb = new StringBuilder();
                }
            } else {
                sb.append((char) ch);
            }
        }
        for (int i = 0; i < word_count; i++) {
            writer.write(word[i] + " " + cnt[i] + "\n");
        }
        writer.close();
        reader.close();

    }
}

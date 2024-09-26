//package org.example.stat;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class WordStatInput {
    private final static int MAXN = 1000;

    public static void main(String[] args) throws IOException {
        String filename_in = args[0], filename_out = args[1];
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename_in), StandardCharsets.UTF_8);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename_out),  StandardCharsets.UTF_8);
        int ch;
        StringBuilder sb = new StringBuilder();
        HashMap<String, Integer> idx = new HashMap<>();
        int[] cnt = new int[MAXN];
        String[] word = new String[MAXN];
        int all = 0;
        while ((ch = reader.read()) != -1) {
            if (!Character.isLetter(ch) &&
                    Character.getType(ch) != Character.DASH_PUNCTUATION &&
                    ch != '\'') {
                if (!sb.isEmpty()) {
                    String str = sb.toString().toLowerCase();
                    if (!idx.containsKey(str)) {
                        word[all] = str;
                        idx.put(str, all++);
                    }
                    int value = idx.get(str);
                    cnt[value]++;
                    sb = new StringBuilder();
                }
            } else {
                sb.append((char) ch);
            }
        }
        for (int i = 0; i < all; i++) {
            writer.write(word[i] + " " + cnt[i] + "\n");
        }
        writer.close();
        reader.close();

    }
}

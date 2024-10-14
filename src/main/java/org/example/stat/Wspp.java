//package org.example.stat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {


    public static void main(String[] args) {
        String filename_in = args[0], filename_out = args[1];
        try {
            MyScanner scanner = new MyScanner(new InputStreamReader(new FileInputStream(filename_in), StandardCharsets.UTF_8));
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename_out), StandardCharsets.UTF_8);
                try {
                    HashMap<String, ArrayList<Integer>> m = new HashMap<>();
                    ArrayList<String> first = new ArrayList<>();
                    for (int i = 0; scanner.hasNextWord(); i++) {
                        String str = scanner.nextWord().toLowerCase();
                        if (!m.containsKey(str)) {
                            first.add(str);
                            m.put(str, new ArrayList<>());
                        }
                        m.get(str).add(i);
                    }

                    for (String s : first) {
                        ArrayList<Integer> el = m.get(s);
                        writer.write(s + " " + el.size());
                        for (int i : el) {
                            writer.write(" " + (i + 1));
                        }
                        writer.write(System.lineSeparator());
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.spec.ECField;
import java.util.*;

public class WsppEvenCurrency {

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        LinkedHashMap<String, HashSet<Integer>> m = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> cnt = new LinkedHashMap<>();

        try {
            MyScanner scanner = new MyScanner(new InputStreamReader(new FileInputStream(filenameIn), StandardCharsets.UTF_8));
            try {
                while (scanner.hasNextLine()) {
                    int i = 0;
                    for (String str : scanner.nextLineWordCurrency()) {
                        str = str.toLowerCase();
                        System.err.print(str + " ");
                        if (!m.containsKey(str)) {
                            cnt.put(str, 0);
                            m.put(str, new HashSet<>());
                        }
                        cnt.put(str, cnt.get(str) + 1);
                        m.get(str).add(i++);
                    }
                    System.err.println();
                }
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }

        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8);
            try {
                for (var word : m.entrySet()) {
                    HashSet<Integer> el = word.getValue();
                    writer.write(word.getKey() + " " + cnt.get(word.getKey()));
                    boolean even = false;
                    for (var i : el) {
                        if (even) {
                            writer.write(" " + (i + 1));
                        }
                        even = !even;
                    }
                    writer.write(System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.err.println("Write error: " + e.getMessage());

        }
    }
}
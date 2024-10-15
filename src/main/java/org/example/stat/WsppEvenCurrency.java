import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WsppEvenCurrency {

    public static void main(String[] args) {
        String filename_in = args[0], filename_out = args[1];
        try {
            MyScanner scanner = new MyScanner(new InputStreamReader(new FileInputStream(filename_in), StandardCharsets.UTF_8));
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename_out), StandardCharsets.UTF_8);
                try {
                    HashMap<String, HashSet<Integer>> m = new HashMap<>();
                    ArrayList<String> first = new ArrayList<>();
                    while (scanner.hasNextLine()) {
                        int i = 0;
                        for (String str : scanner.nextLineWord()) {
                            str = str.toLowerCase();
                            System.err.println(str);
                            if (!m.containsKey(str)) {
                                first.add(str);
                                m.put(str, new HashSet<>());
                            }
                            m.get(str).add(i++);
                        }
                        System.err.println();
                    }

                    for (String s : first) {
                        HashSet<Integer> el = m.get(s);
                        writer.write(s + " " + el.size());
                        boolean even = false;
                        for (int i : el) {
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
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        }
    }
}
